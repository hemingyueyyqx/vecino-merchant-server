package org.example.vecinomerchantserver.repository;

import org.example.vecinomerchantserver.dox.ProductSku;
import org.springframework.data.repository.ListCrudRepository;

public interface ProductSkuRepository extends ListCrudRepository<ProductSku, String> {
    ProductSku findProductSkuById(String id);
}
