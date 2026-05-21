package org.example.vecinomerchantserver.mapper;

import org.example.vecinomerchantserver.dox.ProductSku;
import org.example.vecinomerchantserver.dto.ProductInfo;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ProductInfoResultSetExtractor implements ResultSetExtractor<List<ProductInfo>> {
    @Override
    public List<ProductInfo> extractData(ResultSet rs) throws SQLException, DataAccessException {
        // 用 LinkedHashMap 按 spu_id 去重，保证同一个SPU只创建一次
        Map<String, ProductInfo> productInfoMap = new LinkedHashMap<>();
        while (rs.next()) {
            // 1. 先拿到当前行的 spu_id
            String spuId = rs.getString("s.id");

            // 2. 判断这个SPU是否已经创建过
            ProductInfo productInfo = productInfoMap.get(spuId);
            if (productInfo == null) {
                // 没创建过，就新建一个 ProductInfo
                productInfo = ProductInfo.builder()
                        .spuId(rs.getString("s.id"))
                        .shopId(rs.getString("shop_id"))
                        .shopName(rs.getString("shop_name"))
                        .spuName(rs.getString("spu_name"))
                        .mainImage(rs.getString("main_image"))
                        .detail(rs.getString("detail"))
                        .auditStatus(rs.getInt("audit_status"))
                        .auditRemark(rs.getString("audit_remark"))
                        .spuStatus(rs.getInt("s.status"))
                        .createTime(rs.getObject("s.create_time", LocalDateTime.class))
                        // 初始化SKU列表，避免空指针
                        .skuList(new ArrayList<>())
                        .build();
                // 存到Map里，后面的同SPU行直接复用
                productInfoMap.put(spuId, productInfo);
            }

            // 3. 处理当前行的SKU数据
            String skuId = rs.getString("sk.id");
            // 左连接时，没有SKU的SPU，sku_id会是null，要跳过
            if (skuId != null) {
                ProductSku skuInfo = ProductSku.builder()
                        .id(skuId)
                        .spuId(spuId)
                        .price(rs.getInt("price"))
                        .specAttr(rs.getString("spec_attr"))
                        .stockNum(rs.getInt("stock_num"))
                        .warnStock(rs.getInt("warn_stock"))
                        .status(rs.getInt("sk.status"))
                        .build();
                // 把SKU加到当前SPU的skuList里
                productInfo.getSkuList().add(skuInfo);

            }

        }
        // 把Map里的所有SPU转成List返回
        return new ArrayList<>(productInfoMap.values());
    }
}