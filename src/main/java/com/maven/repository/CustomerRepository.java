package com.maven.repository;

import com.maven.model.*;
import com.maven.model.dtos.CustomerDtos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> getCustomerByUsername(String userame);

    Customer getCustomerById(Long id);
}
