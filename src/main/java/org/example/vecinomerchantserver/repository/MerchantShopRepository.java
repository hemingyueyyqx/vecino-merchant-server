package org.example.vecinomerchantserver.repository;

import org.example.vecinomerchantserver.dto.MerchantShop;
import org.example.vecinomerchantserver.mapper.MerchantShopResultSetExtractor;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface MerchantShopRepository extends ListCrudRepository<MerchantShop, String> {
    @Query(value = """
select u.id ,s.id,u.nickname,u.account,u.phone,s.shop_name ,
       s.first_category ,
       s.second_category,
       s.address,
       s.status,
       s.legal_person ,
       s.business_license,
       s.audit_reason,
       s.update_time

from `user` u
         join shop_info s on u.id = s.business_id
where u.role = 'Sj08';
""",resultSetExtractorClass = MerchantShopResultSetExtractor.class)
    List<MerchantShop> findMerchantShop();
}
