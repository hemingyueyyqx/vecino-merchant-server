package org.example.vecinomerchantserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.vecinomerchantserver.dox.ProductSku;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductInfo {
    private String spuId;
    private String shopId;
    private String shopName;
    private String spuName;
    private String mainImage;
    private String detail;
    private Integer auditStatus;
    private String auditRemark;
    private Integer spuStatus;
    private List<ProductSku> skuList;
    private LocalDateTime createTime;
}
