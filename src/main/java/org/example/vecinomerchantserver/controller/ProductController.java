package org.example.vecinomerchantserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vecinomerchantserver.dox.ProductSpu;
import org.example.vecinomerchantserver.dto.ProductInfo;
import org.example.vecinomerchantserver.service.ProductService;
import org.example.vecinomerchantserver.vo.ResultVo;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/")
public class ProductController {
    private final ProductService productService;
    @GetMapping("product/ProductInfo")
    public ResultVo findProductInfo(@RequestAttribute("uid") String uid, @RequestBody ProductSpu spu) {
        return ResultVo.success(productService.findProductInfo(uid,spu));
    }
    @PostMapping("product/addProduct")
    public ResultVo addProduct(@RequestAttribute("uid") String uid, @RequestBody ProductInfo productInfo) {
        log.info("productInfo:{}",productInfo);
        productService.addProduct(productInfo,uid);
        return ResultVo.ok();
    }
    @PutMapping("product/updateProduct")
    public ResultVo updateProduct(@RequestAttribute("uid") String uid, @RequestBody ProductInfo productInfo) {
        log.info("productInfo:{}",productInfo);
        productService.addProduct(productInfo,uid);
        return ResultVo.ok();
    }
}
