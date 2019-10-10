package com.nick.orders.repos;

import com.nick.orders.models.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepo extends CrudRepository<Customer, Long>{
    Customer findByCustomername(String customername);
}
