package org.example.vecinomerchantserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAddress {
    private String userId;
    private String address;
    private String phone;
    private String nickname;
    private String addressId;
    private LocalDateTime updateTime;

}
