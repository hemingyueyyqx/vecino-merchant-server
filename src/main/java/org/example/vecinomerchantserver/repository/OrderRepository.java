package org.example.vecinomerchantserver.repository;

import org.example.vecinomerchantserver.dox.Order;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface OrderRepository extends ListCrudRepository<Order, String> {
    List<Order> findAllByCustomerId(String customerId);
    List<Order> findAllByShopId(String shopId);

}
