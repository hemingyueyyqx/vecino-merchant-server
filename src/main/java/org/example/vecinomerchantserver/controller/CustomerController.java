package org.example.vecinomerchantserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vecinomerchantserver.dto.UserAddress;
import org.example.vecinomerchantserver.service.UserService;
import org.example.vecinomerchantserver.vo.ResultVo;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/")
public class CustomerController {
    private final UserService userService;
//    查看用户余额
    @GetMapping("user/getBalance")
    public ResultVo getBalance(@RequestAttribute("uid") String uid) {
        Integer balance = userService.getBalance(uid);
        return ResultVo.success(balance);
    }
//    充值
    @PostMapping("user/recharge")
    public ResultVo recharge(@RequestAttribute("uid") String uid, @RequestParam Integer  amount) {
        userService.recharge(uid, amount);
        return ResultVo.ok();
    }
//    获取用户及其地址信息
    @GetMapping("user/getUserInfo")
    public ResultVo getUserInfo(@RequestAttribute("uid") String uid) {
        UserAddress userInfo = userService.getUserInfo(uid);
        return ResultVo.success(userInfo);
    }
//    新建/更新地址
    @PostMapping("user/updateAddress")
    public ResultVo updateAddress(@RequestAttribute("uid") String uid, @RequestParam String address) {
        userService.updateAddress(uid, address);
        return ResultVo.ok();
    }
}
