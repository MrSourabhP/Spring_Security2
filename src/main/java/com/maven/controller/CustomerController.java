package com.maven.controller;

import com.maven.model.Customer;
import com.maven.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @PostMapping("/create-customer")
    public Customer createCustomer(Customer customer){
        return customerService.createCustomer(customer);


    }
}
