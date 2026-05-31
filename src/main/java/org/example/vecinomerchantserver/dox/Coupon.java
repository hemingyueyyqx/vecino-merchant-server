package org.example.vecinomerchantserver.dox;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Coupon {
    @Id
    @CreatedBy
    private String couponId;
    private String couponName;
    private Integer couponType;
    private Integer couponStatus;
    private Integer auditStatus;
    private String auditRemark;
    private Integer fullAmount;
    private Integer reduceAmount;
    private Integer discount;
    private Integer wmkAmount;
    private Integer totalCount;
    private String shopId;
    private String shopName;
    private String startTime;
    private String endTime;
    @ReadOnlyProperty
    private LocalDateTime createTime;
    @ReadOnlyProperty
    private LocalDateTime updateTime;


}
