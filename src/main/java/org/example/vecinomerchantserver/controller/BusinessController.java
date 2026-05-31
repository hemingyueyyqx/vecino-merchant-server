package org.example.vecinomerchantserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vecinomerchantserver.dox.Coupon;
import org.example.vecinomerchantserver.dox.ShopInfo;
import org.example.vecinomerchantserver.dox.User;
import org.example.vecinomerchantserver.dto.MerchantShop;
import org.example.vecinomerchantserver.service.CouponService;
import org.example.vecinomerchantserver.service.MerchantService;
import org.example.vecinomerchantserver.service.UserService;
import org.example.vecinomerchantserver.vo.ResultVo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/")
public class BusinessController {
    private final UserService userService;
    private final MerchantService merchantService;
    private final CouponService couponService;
    @GetMapping("findShop")
    public ResultVo findShop(@RequestAttribute("uid") String uid) {
        Map<String, Object> shopInfoMap = userService.findShop(uid);
        return ResultVo.success(shopInfoMap);
    }
    @GetMapping("user/merchant")
    public ResultVo getMerchantInfo(@RequestAttribute("role") String role) {
        return userService.getAllMerchants(role);

    }
    @GetMapping("user/merchantShop")
    public ResultVo getMerchantShop(@RequestAttribute("role") String role) {
        return merchantService.findMerchantShop(role);
    }
    @PostMapping("coupon/list")
    public ResultVo getCouponList(
            @RequestAttribute("uid") String uid,
            @RequestBody(required = false) Coupon coupon) {
        return ResultVo.success(couponService.getCouponList(uid, coupon));
    }

    @PostMapping("coupon/add")
    public ResultVo addCoupon(@RequestAttribute("uid") String uid, @RequestBody Coupon coupon) {
        log.info("新增优惠券: {}", coupon);
        couponService.addCoupon(coupon, uid);
        return ResultVo.ok();
    }

    @PutMapping("coupon/update")
    public ResultVo updateCoupon(@RequestBody Coupon coupon) {
        log.info("编辑优惠券: {}", coupon);
        couponService.updateCoupon(coupon);
        return ResultVo.ok();
    }

    @DeleteMapping("coupon/delete")
    public ResultVo deleteCoupon(@RequestParam String couponId) {
        log.info("删除优惠券: {}", couponId);
        couponService.deleteCoupon(couponId);
        return ResultVo.ok();
    }
}
