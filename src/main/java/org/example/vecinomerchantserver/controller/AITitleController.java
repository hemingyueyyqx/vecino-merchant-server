package org.example.vecinomerchantserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vecinomerchantserver.dox.ProductSpu;
import org.example.vecinomerchantserver.service.AITitleOptimizationService;
import org.example.vecinomerchantserver.vo.ResultVo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/")
public class AITitleController {
    private final AITitleOptimizationService aiTitleOptimizationService;
    /**
     * AI优化商品标题接口
     */
    @PostMapping("optimize-title")
    public ResultVo optimizeGoodsTitle(@RequestBody String spuName, @RequestAttribute("uid") String uid) {
        List<String> titles = aiTitleOptimizationService.optimizeTitle(spuName, uid);
        return ResultVo.success(titles);
    }
}
