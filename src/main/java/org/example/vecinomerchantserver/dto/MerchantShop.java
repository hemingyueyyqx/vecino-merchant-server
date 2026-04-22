package org.example.vecinomerchantserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.vecinomerchantserver.dox.ShopInfo;
import org.example.vecinomerchantserver.dox.User;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MerchantShop {
    // ====================== 前端要用，但不展示 ======================
    private String userId;    // 商家 user.id
    private String shopId;   // 店铺 shop_info.id

    // ====================== 前端页面展示 ======================
    private String nickname;
    private String shopName;      // 店铺名称
    private String account;       // 商家登录账号
    private String legalPerson;   // 法人
    private String phone;        // 商家电话
    private String shopType;     // 店铺类型
    private String address;      // 店铺地址
    private String businessLicense; // 营业执照
    private Integer status;       // 店铺状态 0正常 1禁用 2待审核
    private String auditReason;
    private LocalDateTime updateTime; // 申请时间
//    private User merchant;
//    private ShopInfo shopInfo;
}
