package org.example.vecinomerchantserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CouponStatusRequest {
    private List< String> couponIds;
    private Integer couponStatus;
}
