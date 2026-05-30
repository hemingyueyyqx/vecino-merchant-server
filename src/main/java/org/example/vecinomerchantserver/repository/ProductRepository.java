package org.example.vecinomerchantserver.repository;

import org.example.vecinomerchantserver.dox.ProductCategory;
import org.example.vecinomerchantserver.dox.ProductSpu;
import org.example.vecinomerchantserver.dto.ProductInfo;
import org.example.vecinomerchantserver.mapper.MerchantShopResultSetExtractor;
import org.example.vecinomerchantserver.mapper.ProductInfoResultSetExtractor;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends ListCrudRepository<ProductCategory, String> {
    List<ProductCategory> findByParentId(String parentId);
    @Query(value = """

            SELECT
    -- SPU 字段（按你截图里的字段写）
    s.id,
    s.spu_name,
    s.shop_id,
    s.shop_name,
    s.main_image,
    s.detail,
    s.audit_status,
    s.audit_remark,
    s.status,
    s.create_time,
    -- SKU 字段（按你实际表结构写全）
    sk.id,
    sk.spu_id,
    sk.spec_attr,
    sk.price,
    sk.stock_num,
    sk.warn_stock,
    sk.status
FROM product_spu s
LEFT JOIN product_sku sk
  ON s.id = sk.spu_id
where s.shop_id = :shopId
-- 模糊查询：spuName 可选，为空就忽略这个条件
           AND (:spuName IS NULL OR s.spu_name LIKE CONCAT('%', :spuName, '%'))
           -- ✅ 新增 SPU 状态条件（动态判断，参数为空时忽略）
           AND (:spuStatus IS NULL OR s.status = :spuStatus)
           -- ✅ 新增 审核/订单状态 条件（动态判断，参数为空时忽略）
           AND (:auditStatus IS NULL OR s.audit_status = :auditStatus)
           -- AND (:shopName IS NULL OR s.shop_name = :shopName)
-- 关键：按 spu_id 排序，同一个SPU的行连在一起，组装效率更高
ORDER BY s.id;
""",resultSetExtractorClass = ProductInfoResultSetExtractor.class)
    List<ProductInfo> findProductInfo( String shopId, String spuName, Integer spuStatus, Integer auditStatus);
    @Query(value = """

            SELECT
    -- SPU 字段（按你截图里的字段写）
    s.id,
    s.spu_name,
    s.shop_id,
    s.shop_name,
    s.main_image,
    s.detail,
    s.audit_status,
    s.audit_remark,
    s.status,
    s.create_time,
    -- SKU 字段（按你实际表结构写全）
    sk.id,
    sk.spu_id,
    sk.spec_attr,
    sk.price,
    sk.stock_num,
    sk.warn_stock,
    sk.status
FROM product_spu s
LEFT JOIN product_sku sk
  ON s.id = sk.spu_id
-- where s.shop_id = :shopId
-- 模糊查询：spuName 可选，为空就忽略这个条件
where
           (:spuName IS NULL OR s.spu_name LIKE CONCAT('%', :spuName, '%'))
           -- ✅ 新增 SPU 状态条件（动态判断，参数为空时忽略）
           AND (:spuStatus IS NULL OR s.status = :spuStatus)
           -- ✅ 新增 审核/订单状态 条件（动态判断，参数为空时忽略）
           AND (:auditStatus IS NULL OR s.audit_status = :auditStatus)
           AND (:shopName IS NULL OR s.shop_name = :shopName)
-- 关键：按 spu_id 排序，同一个SPU的行连在一起，组装效率更高
ORDER BY s.id;
""",resultSetExtractorClass = ProductInfoResultSetExtractor.class)
    List<ProductInfo> findProductInfos(String spuName, Integer spuStatus, Integer auditStatus, String shopName);
}
