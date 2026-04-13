package org.example.vecinomerchantserver.repository;

import org.example.vecinomerchantserver.dox.ShopInfo;
import org.springframework.data.repository.ListCrudRepository;

public interface ShopInfoRepository extends ListCrudRepository<ShopInfo, String> {
    public ShopInfo findByBusinessId(String uid);
}
