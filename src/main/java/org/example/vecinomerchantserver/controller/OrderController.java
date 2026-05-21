package org.example.vecinomerchantserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vecinomerchantserver.dox.Order;
import org.example.vecinomerchantserver.service.OrderService;
import org.example.vecinomerchantserver.vo.ResultVo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/")
public class OrderController {
    private final OrderService orderService;
//    下单
    @PostMapping("addOrder")
    public ResultVo addOrder(@RequestAttribute("uid") String uid, @RequestBody Order order) {
         orderService.addOrder(uid, order);
         return ResultVo.ok();
    }
//    获取订单列表
    @GetMapping("getOrderList")
    public ResultVo getOrderList(@RequestAttribute("uid") String uid) {
        List<Order> orderList = orderService.getOrderList(uid);
        return ResultVo.success(orderList);
    }
}
