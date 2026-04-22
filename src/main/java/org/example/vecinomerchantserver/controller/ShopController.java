package org.example.vecinomerchantserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vecinomerchantserver.dox.ShopInfo;
import org.example.vecinomerchantserver.service.ShopService;
import org.example.vecinomerchantserver.service.UserService;
import org.example.vecinomerchantserver.vo.ResultVo;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/")
public class ShopController {
    private final ShopService shopService;
    @PostMapping("apply")
    public ResultVo apply(@RequestBody ShopInfo shopInfo,@RequestAttribute("uid") String uid) {
        return shopService.apply(shopInfo, uid);
    }
//    审核通过
    @PutMapping("shop/audit/pass")
    public ResultVo auditPass(@RequestBody ShopInfo shopInfo) {
        return shopService.auditPass(shopInfo);
    }
//    审核驳回
    @PutMapping("shop/audit/reject")
    public ResultVo auditReject(@RequestBody ShopInfo shopInfo) {
        return shopService.auditReject(shopInfo);
    }

}
