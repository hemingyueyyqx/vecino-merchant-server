package org.example.vecinomerchantserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vecinomerchantserver.dox.Order;
import org.example.vecinomerchantserver.dox.Review;
import org.example.vecinomerchantserver.service.AIReviewAnalysis;
import org.example.vecinomerchantserver.service.OrderService;
import org.example.vecinomerchantserver.service.ReviewService;
import org.example.vecinomerchantserver.vo.ResultVo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/")
public class OrderController {
    private final OrderService orderService;
    private final ReviewService reviewService;
    private final AIReviewAnalysis AIReviewAnalysis;
//    下单
    @PostMapping("addOrder")
    public ResultVo addOrder(@RequestAttribute("uid") String uid, @RequestBody Order order) {
         orderService.addOrder(uid, order);
         return ResultVo.ok();
    }
//    获取订单列表
    @GetMapping("getOrderList")
    public ResultVo getOrderList(@RequestAttribute("uid") String uid,@RequestAttribute("role") String role) {
        List<Order> orderList = orderService.getOrderList(uid, role);
        return ResultVo.success(orderList);
    }
//    取消订单
    @PostMapping("cancelOrder")
    public ResultVo cancelOrder(@RequestAttribute("uid") String uid, @RequestParam String orderId) {
        orderService.cancelOrder(uid, orderId);
        return ResultVo.ok();
    }
//    确认收货
    @PostMapping("confirmOrder")
    public ResultVo confirmOrder(@RequestParam String orderId) {
        orderService.confirmOrder(orderId);
        return ResultVo.ok();
    }
//    修改订单状态
@PutMapping("order/status")
public ResultVo updateOrderStatus(@RequestParam String orderId, @RequestParam Integer orderStatus) {
    orderService.updateOrderStatus(orderId, orderStatus);
    return ResultVo.ok();
}
//新建评价
    @PostMapping("addComment")
    public ResultVo addComment(@RequestAttribute("uid") String uid, @RequestBody Review review) {
        reviewService.addComment(uid, review);
        return ResultVo.ok();
    }
//    更新评价
    @PutMapping("updateComment")
    public ResultVo updateComment( @RequestParam String reviewId, @RequestParam String replyContent,@RequestParam String analysis) {
        reviewService.updateComment( reviewId, replyContent, analysis);
        return ResultVo.ok();
    }
//    获取评价
    @GetMapping("getReview")
    public ResultVo getReview(@RequestParam String orderId) {
        Review review = reviewService.getReview(orderId);
        return ResultVo.success(review);
    }
    @GetMapping("getReviewList")
    public ResultVo getReviewList(@RequestAttribute("uid") String uid) {
        List<Review> reviewList = reviewService.getReviewList(uid);
        return ResultVo.success(reviewList);
    }
//    AI评价分析
    @GetMapping("ai/analysis")
    public ResultVo aiAnalysis(@RequestParam String reviewId) {
        Map<String, String> result = AIReviewAnalysis.aiAnalysis(reviewId);
        return ResultVo.success( result);
    }
}
