package com.nick.orders.services;

import com.nick.orders.models.Customer;
import com.nick.orders.models.Order;
import com.nick.orders.repos.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "customerService")
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepo customerRepos;

    @Override
    public List<Customer> findAll() {
        List<Customer> rtnList = new ArrayList<>();
        customerRepos.findAll().iterator().forEachRemaining(rtnList::add);
        return rtnList;
    }

    @Override
    public Customer findCustomerByName(String customername) {
        Customer rtnCustomer = customerRepos.findByCustomername(customername);
        if(rtnCustomer == null) {
            throw new EntityNotFoundException("Customer Not Found: " + customername);
        }
        return rtnCustomer;
    }

    @Transactional
    @Override
    public Customer save(Customer customer) {

        Customer newCustomer = new Customer();
        newCustomer.setAgent(customer.getAgent());
        newCustomer.setCustomercity(customer.getCustomercity());
        newCustomer.setCustomercountry(customer.getCustomercountry());
        newCustomer.setGrade(customer.getGrade());
        newCustomer.setCustomername(customer.getCustomername());
        newCustomer.setOpeningamt(customer.getOpeningamt());
        newCustomer.setOutstandingamt(customer.getOutstandingamt());
        newCustomer.setPaymentamt(customer.getPaymentamt());
        newCustomer.setPhone(customer.getPhone());
        newCustomer.setReceiveamt(customer.getReceiveamt());
        newCustomer.setWorkingarea(customer.getWorkingarea());

        for(Order o: customer.getOrders()) {
           newCustomer.getOrders().add(new Order(o.getOrdamount(), o.getAdvanceamount(), newCustomer, o.getOrddescription()));
        }

        return customerRepos.save(newCustomer);
    }

    @Override
    public Customer update(Customer customer, long customercode) {

        Customer currentCustomer = customerRepos.findById(customercode)
                .orElseThrow(() -> new EntityNotFoundException(Long.toString(customercode)));
        if(customer.getAgent() != null) {
            currentCustomer.setAgent(customer.getAgent());
        }
        if(customer.getCustomercity() != null) {
            currentCustomer.setCustomercity(customer.getCustomercity());
        }
        if(customer.getCustomercountry() != null) {
            currentCustomer.setCustomercountry(customer.getCustomercountry());
        }
        if(customer.getCustomername() != null) {
            currentCustomer.setCustomername(customer.getCustomername());
        }
        if(customer.getGrade() != null) {
            currentCustomer.setGrade(customer.getGrade());
        }
        if(customer.getOpeningamt() != null) {
            currentCustomer.setOpeningamt(customer.getOpeningamt());
        }
        if(customer.getOutstandingamt() != null) {
            currentCustomer.setOutstandingamt(customer.getOutstandingamt());
        }
        if(customer.getPaymentamt() != null) {
            currentCustomer.setPaymentamt(customer.getPaymentamt());
        }
        if(customer.getPhone() != null) {
            currentCustomer.setPhone(customer.getPhone());
        }
        if(customer.getWorkingarea() != null) {
            currentCustomer.setWorkingarea(customer.getWorkingarea());
        }
        if(customer.getReceiveamt() != null) {
            currentCustomer.setReceiveamt(customer.getReceiveamt());
        }
        if(customer.getOrders().size() > 0) {
            for(Order o: customer.getOrders()) {
                currentCustomer.getOrders().add(new Order(o.getOrdamount(), o.getAdvanceamount(), currentCustomer, o.getOrddescription()));
            }
        }
        return customerRepos.save(currentCustomer);
    }

    @Override
    public void delete(long customercode) {
        if(customerRepos.findById(customercode).isPresent()) {
            customerRepos.deleteById(customercode);
        } else {
            throw new EntityNotFoundException("Cannot delete customer with this ID: " + customercode);
        }
    }
}
