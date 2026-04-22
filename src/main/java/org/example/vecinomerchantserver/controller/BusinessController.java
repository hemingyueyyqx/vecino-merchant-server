package org.example.vecinomerchantserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vecinomerchantserver.dox.ShopInfo;
import org.example.vecinomerchantserver.dox.User;
import org.example.vecinomerchantserver.dto.MerchantShop;
import org.example.vecinomerchantserver.service.MerchantService;
import org.example.vecinomerchantserver.service.UserService;
import org.example.vecinomerchantserver.vo.ResultVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/")
public class BusinessController {
    private final UserService userService;
    private final MerchantService merchantService;
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
}
