package org.example.vecinomerchantserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vecinomerchantserver.dox.Coupon;
import org.example.vecinomerchantserver.dox.PlatformActivity;
import org.example.vecinomerchantserver.dox.ShopInfo;
import org.example.vecinomerchantserver.dto.ActivityConfigDTO;
import org.example.vecinomerchantserver.repository.PlatformActivityRepository;
import org.example.vecinomerchantserver.repository.CouponRepository;
import org.example.vecinomerchantserver.repository.ShopInfoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CouponService {
    private final CouponRepository couponRepository;
    private final ShopInfoRepository shopInfoRepository;
    private final PlatformActivityRepository platformActivityRepository;

    @Transactional(readOnly = true)
    public List<Coupon> getCouponList(String uid, Coupon coupon) {
        ShopInfo shopInfo = shopInfoRepository.findByBusinessId(uid);
        String shopId = null;
        if(shopInfo != null) {
            shopId = shopInfo.getId();
        }
        String couponName = coupon.getCouponName();
        Integer couponStatus = coupon.getCouponStatus();
        Integer auditStatus = coupon.getAuditStatus();
        String shopName = coupon.getShopName();
        return couponRepository.findCoupons(couponName, couponStatus, auditStatus, shopId, shopName);
    }

    @Transactional
    public void addCoupon(Coupon coupon, String uid) {
        ShopInfo shopInfo = shopInfoRepository.findByBusinessId(uid);
        if (shopInfo == null) {
            throw new RuntimeException("店铺信息不存在");
        }

        coupon.setShopId(shopInfo.getId());
        coupon.setShopName(shopInfo.getShopName());
        if (coupon.getCouponStatus() == null) {
            coupon.setCouponStatus(0);
        }
        if (coupon.getAuditStatus() == null) {
            coupon.setAuditStatus(0);
        }
        Coupon c = coupon.builder()
                .couponName(coupon.getCouponName())
                .couponType(coupon.getCouponType())
                .fullAmount(coupon.getFullAmount())
                .reduceAmount(coupon.getReduceAmount())
                .discount(coupon.getDiscount())
                .wmkAmount(coupon.getWmkAmount())
                .totalCount(coupon.getTotalCount())
                .shopId(coupon.getShopId())
                .shopName(coupon.getShopName())
                .startTime(coupon.getStartTime())
                .endTime(coupon.getEndTime())
                .auditStatus(coupon.getAuditStatus())
                .couponStatus(coupon.getCouponStatus())
                .build();

        couponRepository.save(c);
    }

    @Transactional
    public void updateCoupon(Coupon coupon) {
        Coupon existingCoupon = couponRepository.findById(coupon.getCouponId()).orElse(null);
        if (existingCoupon == null) {
            throw new RuntimeException("优惠券不存在");
        }

        if (coupon.getCouponName() != null) {
            existingCoupon.setCouponName(coupon.getCouponName());
        }
        if (coupon.getCouponType() != null) {
            existingCoupon.setCouponType(coupon.getCouponType());
        }
        if (coupon.getCouponStatus() != null) {
            existingCoupon.setCouponStatus(coupon.getCouponStatus());
        }
        if (coupon.getAuditStatus() != null) {
            existingCoupon.setAuditStatus(coupon.getAuditStatus());
        }else{
            existingCoupon.setAuditStatus(0);
        }
        if (coupon.getFullAmount() != null) {
            existingCoupon.setFullAmount(coupon.getFullAmount());
        }
        if (coupon.getReduceAmount() != null) {
            existingCoupon.setReduceAmount(coupon.getReduceAmount());
        }
        if (coupon.getDiscount() != null) {
            existingCoupon.setDiscount(coupon.getDiscount());
        }
        if (coupon.getWmkAmount() != null) {
            existingCoupon.setWmkAmount(coupon.getWmkAmount());
        }
        if (coupon.getTotalCount() != null) {
            existingCoupon.setTotalCount(coupon.getTotalCount());
        }
        if (coupon.getStartTime() != null) {
            existingCoupon.setStartTime(coupon.getStartTime());
        }
        if (coupon.getEndTime() != null) {
            existingCoupon.setEndTime(coupon.getEndTime());
        }

        couponRepository.save(existingCoupon);
    }

    @Transactional
    public void deleteCoupon(String couponId) {
        couponRepository.deleteById(couponId);
    }
    @Transactional
    public void addActivity(ActivityConfigDTO activityConfigDTO) {
        PlatformActivity activity = PlatformActivity.builder()
                .activityName(activityConfigDTO.getActivityName())
                .activityTheme(activityConfigDTO.getActivityTheme())
                .activityType(activityConfigDTO.getActivityType())
                .startTime(activityConfigDTO.getStartTime())
                .endTime(activityConfigDTO.getEndTime())
                .fullReductionRule(activityConfigDTO.getFullReductionRule())
                .targetCategory(String.join(",", activityConfigDTO.getTargetCategory()))
                .targetMerchantType(activityConfigDTO.getTargetMerchantType())
                .budget(activityConfigDTO.getBudget())
                .activityDesc(activityConfigDTO.getActivityDesc())
                .build();
        platformActivityRepository.save(activity);

    }
}
