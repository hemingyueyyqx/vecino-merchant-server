package org.example.vecinomerchantserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vecinomerchantserver.dox.ProductSku;
import org.example.vecinomerchantserver.dox.ProductSpu;
import org.example.vecinomerchantserver.dto.BatchAuditStatusRequest;
import org.example.vecinomerchantserver.dto.BatchStatusRequest;
import org.example.vecinomerchantserver.dto.ProductInfo;
import org.example.vecinomerchantserver.service.ProductService;
import org.example.vecinomerchantserver.vo.ResultVo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/")
public class ProductController {
    private final ProductService productService;
    @PostMapping("product/productInfo")
    public ResultVo findProductInfo(@RequestAttribute("uid") String uid, @RequestBody(required = false) ProductInfo spu) {
        return ResultVo.success(productService.findProductInfo(uid,spu));
    }
    @PostMapping("product/productInfos")
    public ResultVo findProductInfos(@RequestBody(required = false) ProductInfo spu) {
        return ResultVo.success(productService.findProductInfos(spu));
    }
    @PostMapping("product/addProduct")
    public ResultVo addProduct(@RequestAttribute("uid") String uid, @RequestBody ProductInfo productInfo) {
        log.info("productInfo:{}",productInfo);
        productService.addProduct(productInfo,uid);
        return ResultVo.ok();
    }
    @PutMapping("product/updateSPU")
    public ResultVo updateProductSpu(@RequestBody ProductSpu spu) {
        log.info("productInfo:{}",spu);
        productService.updateProductSpu(spu);
        return ResultVo.ok();
    }
    @PutMapping("product/updateSKU")
    public ResultVo updateProductSku( @RequestBody ProductSku sku) {
        log.info("productInfo:{}",sku);
        productService.updateProductSku(sku);
        return ResultVo.ok();
    }
// 商品批量上下架
    @PutMapping("product/batch/status")
    public ResultVo batchUpdateStatus(@RequestBody BatchStatusRequest  request) {
        productService.batchUpdateStatus(request);
        log.info("request:{}",request);
        return ResultVo.ok();
    }
//    商品批量审核/驳回
    @PutMapping("product/batch/audit")
    public ResultVo batchAuditStatus(@RequestBody BatchAuditStatusRequest request) {
        log.info("request:{}",request);
        productService.batchUpdateAuditStatus(request);
        return ResultVo.ok();
    }

}
