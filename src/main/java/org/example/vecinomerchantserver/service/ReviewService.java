package org.example.vecinomerchantserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vecinomerchantserver.dox.Review;
import org.example.vecinomerchantserver.dox.ShopInfo;
import org.example.vecinomerchantserver.dox.User;
import org.example.vecinomerchantserver.repository.OrderRepository;
import org.example.vecinomerchantserver.repository.ReviewRepository;
import org.example.vecinomerchantserver.repository.ShopInfoRepository;
import org.example.vecinomerchantserver.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;
    private final ShopInfoRepository shopInfoRepository;
    private final UserRepository userRepository;
//    新建评价
    @Transactional
    public void addComment(String uid, Review review) {
        User user = userRepository.findById(uid).orElse(null);
        Review newReview = Review.builder()
                .orderId(review.getOrderId())
                .orderNo(review.getOrderNo())
                .customerId(uid)
                .shopId(review.getShopId())
                .nickname(user.getNickname())
                .image(review.getImage())
                .content(review.getContent())
                .reviewType(review.getReviewType())
                .build();
        reviewRepository.save(newReview);
    }
//    更新评价
    @Transactional
    public void updateComment(String reviewId, String replyContent, String analysis) {
        Review review = reviewRepository.findById(reviewId).get();
        review.setReplyContent(replyContent);
        review.setAnalysis(analysis);
        reviewRepository.save(review);
    }
//  获取评价
    public Review getReview(String orderId) {
        return reviewRepository.findReviewByOrderId(orderId);
    }
    public List<Review> getReviewList(String uid) {
        ShopInfo shopInfo = shopInfoRepository.findByBusinessId(uid);
        return reviewRepository.findAllByShopId(shopInfo.getId());
    }

}
