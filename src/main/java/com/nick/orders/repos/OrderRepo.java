package com.nick.orders.repos;

import com.nick.orders.models.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepo extends CrudRepository<Order, Long>{
}
