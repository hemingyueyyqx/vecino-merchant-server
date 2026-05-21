package org.example.vecinomerchantserver.repository;

import org.example.vecinomerchantserver.dox.User;
import org.example.vecinomerchantserver.dto.UserAddress;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface UserRepository extends ListCrudRepository<User, String> {
    User findByAccount(String account);
    User findByPhone(String phone);
    List<User> findAllByRole(String role);
    @Query("""
            SELECT
                u.id,
                u.nickname,
                u.phone,
                a.address,
                a.id,
                a.update_time
            FROM user u
            LEFT JOIN address a on u.id = a.customer_id
            WHERE u.id = :userId
            """)
    UserAddress findUserAddressByUserId(String userId);
}
