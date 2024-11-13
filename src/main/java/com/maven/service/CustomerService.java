package com.maven.service;

import com.maven.model.*;
import com.maven.model.dtos.CustomerDtos;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {
    Customer createCustomer(Customer customer);

    Customer getCustomerById(Long id);

    List<Customer> getAllCustomers();

    void deleteCustomer(Long id);

    Customer updatateCustomer(Customer customer);


}
