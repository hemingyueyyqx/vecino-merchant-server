package org.example.vecinomerchantserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vecinomerchantserver.dox.ShopInfo;
import org.example.vecinomerchantserver.service.UserService;
import org.example.vecinomerchantserver.vo.ResultVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/")
public class BusinessController {
    private final UserService userService;
    @GetMapping("findShop")
    public ResultVo findShop(@RequestAttribute("uid") String uid) {
        Map<String, Object> shopInfoMap = userService.findShop(uid);
        return ResultVo.success(shopInfoMap);
    }
}
