package org.example.vecinomerchantserver.repository;

import org.example.vecinomerchantserver.dox.Review;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface ReviewRepository extends ListCrudRepository<Review, String> {
    List<Review> findAllByShopId(String shopId);
    Review findReviewByOrderId(String orderId);
}
