package org.example.vecinomerchantserver.repository;

import org.example.vecinomerchantserver.dox.User;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface UserRepository extends ListCrudRepository<User, String> {
    User findByAccount(String account);
    User findByPhone(String phone);
    List<User> findAllByRole(String role);
}
