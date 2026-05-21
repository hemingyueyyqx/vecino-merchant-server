package org.example.vecinomerchantserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vecinomerchantserver.dox.ProductSku;
import org.example.vecinomerchantserver.dox.ProductSpu;
import org.example.vecinomerchantserver.dox.ShopInfo;
import org.example.vecinomerchantserver.dox.User;
import org.example.vecinomerchantserver.dto.BatchStatusRequest;
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
    public List<ProductInfo> findProductInfo(String uid, ProductInfo spu) {
        String shopId;
        ShopInfo shopInfo = shopInfoRepository.findByBusinessId(uid);
        if(shopInfo != null && shopInfo.getId() != null){
            shopId = shopInfo.getId();
        } else if(spu.getShopId() != null){
            shopId = spu.getShopId();
        } else {
            shopId = null;
        }
        String spuName = spu.getSpuName();
        Integer spuStatus = spu.getSpuStatus();
        Integer auditStatus = spu.getAuditStatus();
        return productRepository.findProductInfo(shopId, spuName, spuStatus, auditStatus);
    }
//    添加商品
    @Transactional
    public void addProduct(ProductInfo productInfo, String uid) {
        ShopInfo shopInfo = shopInfoRepository.findByBusinessId(uid);
        // 创建并保存SPU
        ProductSpu spu = ProductSpu.builder()
                .shopId(shopInfo.getId())
                .shopName(shopInfo.getShopName())
                .spuName(productInfo.getSpuName())
                .mainImage(productInfo.getMainImage())
                .detail(productInfo.getDetail())
                .auditStatus(productInfo.getAuditStatus() != null ? productInfo.getAuditStatus() : 1) // 默认待审核
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
//    编辑spu
    @Transactional
    public void updateProductSpu(ProductSpu spu) {
        ProductSpu productSpu = productSpuRepository.findById(spu.getId()).orElse(null);
        if (productSpu != null) {
            if (spu.getSpuName() != null) {
                productSpu.setSpuName(spu.getSpuName());
            }
            if (spu.getMainImage() != null) {
                productSpu.setMainImage(spu.getMainImage());
            }
            if (spu.getDetail() != null) {
                productSpu.setDetail(spu.getDetail());
            }
            if (spu.getAuditStatus() != null) {
                productSpu.setAuditStatus(spu.getAuditStatus());
            }
            if (spu.getAuditRemark() != null) {
                productSpu.setAuditRemark(spu.getAuditRemark());
            }
            if (spu.getStatus() != null) {
                productSpu.setStatus(spu.getStatus());
            }
            productSpuRepository.save(productSpu);
        }

    }
//    编辑sku
    @Transactional
    public void updateProductSku(ProductSku sku) {
        ProductSku productSku = productSkuRepository.findById(sku.getId()).orElse(null);
        if (productSku != null) {
            if (sku.getSpecAttr() != null) {
                productSku.setSpecAttr(sku.getSpecAttr());
            }
            if (sku.getPrice() != null) {
                productSku.setPrice(sku.getPrice());
            }
            if (sku.getStockNum() != null) {
                productSku.setStockNum(sku.getStockNum());
            }
            if (sku.getWarnStock() != null) {
                productSku.setWarnStock(sku.getWarnStock());
            }
            if (sku.getStatus() != null) {
                productSku.setStatus(sku.getStatus());
            }
            productSkuRepository.save(productSku);
        }
    }
//    商品批量上下架
    @Transactional
    public void batchUpdateStatus(BatchStatusRequest  request) {
        List<String> spuIds = request.getSpuIds();
        Integer status = request.getStatus();
        for (String spuId : spuIds) {
            ProductSpu productSpu = productSpuRepository.findById(spuId).orElse(null);
            if (productSpu != null) {
                productSpu.setStatus(status);
                productSpuRepository.save(productSpu);
            }
        }
    }
}
