package org.example.vecinomerchantserver.dox;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @CreatedBy
    private String orderId;
    private String orderNo;
    private String customerId;
    private String shopId;
    private String shopName;
    private String spuId;
    private String spuName;
    private String mainImage;
    private String skuId;
    private String specAttr;
    private Integer price;
    private Integer quantity;
    private Integer totalAmount;
    private String address;
    private String orderStatus;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;


}
