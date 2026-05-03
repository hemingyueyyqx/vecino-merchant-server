package org.example.vecinomerchantserver.repository;

import org.example.vecinomerchantserver.dox.ProductCategory;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface ProductRepository extends ListCrudRepository<ProductCategory, String> {
    List<ProductCategory> findByParentId(String parentId);
}
