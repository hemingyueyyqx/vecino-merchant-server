package org.example.vecinomerchantserver.dox;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.relational.core.sql.In;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    public static final Integer YIQUXIAO = 0;
    public static final Integer DAIJIEDAN = 1;
    public static final Integer BEIHUOZHONG = 2;
    public static final Integer PEISONGZHONG = 3;
    public static final Integer JIAOYIWANCHENG = 4;
    public static final Integer YIPINGJIA = 5;
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
    private Integer orderStatus;
    @ReadOnlyProperty
    private LocalDateTime createTime;
    @ReadOnlyProperty
    private LocalDateTime updateTime;


}
