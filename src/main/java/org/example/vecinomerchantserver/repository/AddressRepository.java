package org.example.vecinomerchantserver.repository;

import org.example.vecinomerchantserver.dox.Address;
import org.springframework.data.repository.ListCrudRepository;

public interface AddressRepository extends ListCrudRepository<Address, String> {
    Address findAddressByCustomerId(String customerId);
}
