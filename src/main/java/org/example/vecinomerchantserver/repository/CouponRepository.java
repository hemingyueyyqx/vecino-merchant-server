package org.example.vecinomerchantserver.repository;

import org.example.vecinomerchantserver.dox.Coupon;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface CouponRepository extends ListCrudRepository<Coupon, String> {
    @Query("""
        SELECT * FROM coupon
        WHERE (:shopId is null or shop_id = :shopId)
          AND(:couponName IS NULL OR coupon_name LIKE CONCAT('%', :couponName, '%'))
          AND (:couponStatus IS NULL OR coupon_status = :couponStatus)
          AND (:auditStatus IS NULL OR audit_status = :auditStatus)
          AND (:shopName is null or shop_name = :shopName)
        ORDER BY create_time DESC
        """)
    List<Coupon> findCoupons(String couponName, Integer couponStatus, Integer auditStatus, String shopId,String shopName);
}
