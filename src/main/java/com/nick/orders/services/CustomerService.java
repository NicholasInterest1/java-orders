package com.nick.orders.services;

import com.nick.orders.models.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> findAll();

    Customer findCustomerByName(String name);

    Customer save(Customer customer);

    Customer update(Customer customer, long customercode);

    void delete(long customercode);
}
