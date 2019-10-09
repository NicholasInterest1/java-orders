package com.nick.orders.controllers;

import com.nick.orders.models.Customer;
import com.nick.orders.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    //http://localhost:2019/customer/order+
    @GetMapping(value = "/customer/order", produces = {"application/json"})
    public ResponseEntity<?> listAllCustomers() {
        List<Customer> customersList = customerService.findAll();
        return new ResponseEntity<>(customersList, HttpStatus.OK);
    }
    //http://localhost:2019/customer/name/{customername}
    @GetMapping(value = "/customer/name/{customername}", produces = {"application/json"})
    public ResponseEntity<?> findCustomerByName(@PathVariable String customername) {
        Customer rtnCustomer = customerService.findCustomerByName(customername);
        return new ResponseEntity<>(rtnCustomer, HttpStatus.OK);
    }

    //htttp://localhost:2019/data/customer/newcustomer
    @PostMapping(value = "/data/customer/newcustomer", produces = {"application/json"})
    public ResponseEntity<?> addNewCustomer(@Valid @RequestBody Customer newCustomer) throws URISyntaxException {
        newCustomer = customerService.save(newCustomer);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newCustomerURI = ServletUriComponentsBuilder.fromHttpUrl("http://localhost:2019/customer/name").path("/{customername}").buildAndExpand(newCustomer.getCustomername()).toUri();
        responseHeaders.setLocation(newCustomerURI);
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    //http://localhost:2019/data/customer/update/{customercode}
    @PutMapping(value = "/data/customer/update/{customercode}", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<?> updateCustomer(@RequestBody Customer updatedCustomer, @PathVariable long customercode) {
        customerService.update(updatedCustomer, customercode);
        return new ResponseEntity<>("Successfully Updated Customer", HttpStatus.OK);
    }

    //http://localhost:2019/data/customer/delete/{customercode}
    @DeleteMapping(value = "/data/customer/delete/{customercode}", produces = {"application/json"})
    public ResponseEntity<?> deleteCustomer(@PathVariable long customercode) {
        customerService.delete(customercode);
        return new ResponseEntity<>("Successfully Deleted Customer", HttpStatus.OK);
    }
}
