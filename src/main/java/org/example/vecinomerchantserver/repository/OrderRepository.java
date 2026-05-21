package org.example.vecinomerchantserver.repository;

import org.example.vecinomerchantserver.dox.Order;
import org.springframework.data.repository.ListCrudRepository;

public interface OrderRepository extends ListCrudRepository<Order, String> {
}
