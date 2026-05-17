package org.example.vecinomerchantserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vecinomerchantserver.dox.ProductSku;
import org.example.vecinomerchantserver.dox.ProductSpu;
import org.example.vecinomerchantserver.dox.ShopInfo;
import org.example.vecinomerchantserver.dox.User;
import org.example.vecinomerchantserver.dto.ProductInfo;
import org.example.vecinomerchantserver.repository.ProductRepository;
import org.example.vecinomerchantserver.repository.ProductSkuRepository;
import org.example.vecinomerchantserver.repository.ProductSpuRepository;
import org.example.vecinomerchantserver.repository.ShopInfoRepository;
import org.example.vecinomerchantserver.vo.ResultVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ShopInfoRepository shopInfoRepository;
    private final ProductSpuRepository productSpuRepository;
    private final ProductSkuRepository productSkuRepository;
    @Transactional
    public List<ProductInfo> findProductInfo(String uid, ProductSpu spu) {
        ShopInfo shopInfo = shopInfoRepository.findByBusinessId(uid);
        return productRepository.findProductInfo(shopInfo.getId(), spu.getSpuName());
    }
//    添加商品
    @Transactional
    public void addProduct(ProductInfo productInfo, String uid) {
        ShopInfo shopInfo = shopInfoRepository.findByBusinessId(uid);
        // 创建并保存SPU
        ProductSpu spu = ProductSpu.builder()
                .shopId(shopInfo.getId())
                .spuName(productInfo.getSpuName())
                .categoryId(productInfo.getCategoryId())
                .mainImage(productInfo.getMainImage())
                .detail(productInfo.getDetail())
                .auditStatus(productInfo.getAuditStatus() != null ? productInfo.getAuditStatus() : 0) // 默认待审核
                .auditRemark(productInfo.getAuditRemark())
                .status(productInfo.getSpuStatus() != null ? productInfo.getSpuStatus() : 1) // 默认上架状态
                .build();

        // 保存SPU到数据库
        productSpuRepository.save(spu);
        // 遍历SKU列表并保存
        if (productInfo.getSkuList() != null && !productInfo.getSkuList().isEmpty()) {
            for (ProductSku sku : productInfo.getSkuList()) {
                ProductSku skuInfo = ProductSku.builder()
                        .spuId(spu.getId())
                        .specAttr(sku.getSpecAttr())
                        .price(sku.getPrice())
                        .stockNum(sku.getStockNum())
                        .warnStock(sku.getWarnStock())
                        .status(sku.getStatus() != null ? sku.getStatus() : 1) // 默认启用
                        .build();

                // 保存SKU到数据库
                productSkuRepository.save(skuInfo);
            }
        }
    }
}
