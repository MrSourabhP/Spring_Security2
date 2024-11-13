package com.maven.service.impl;

import com.maven.model.*;
import com.maven.model.dtos.CustomerDtos;
import com.maven.repository.CustomerRepository;
import com.maven.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerRepository.getCustomerById(id);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public void deleteCustomer(Long id) {
        Customer customerById = customerRepository.getCustomerById(id);
        customerRepository.delete(customerById);
    }

    @Override
    public Customer updatateCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
}
