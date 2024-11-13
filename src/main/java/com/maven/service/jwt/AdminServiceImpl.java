package com.maven.service.jwt;

import com.maven.model.*;
import com.maven.repository.CustomerRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminServiceImpl implements UserDetailsService {
    private final CustomerRepository customerRepository;

    public AdminServiceImpl(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;

    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> customerByUsername = customerRepository.getCustomerByUsername(username);
        if(customerByUsername.isEmpty()){
            System.out.println("User Not Found");
            throw new UsernameNotFoundException("User Not Found");
        }
        return new User(customerByUsername.get().getUsername(), customerByUsername.get().getPassword(), customerByUsername.get().getAuthorities());
    }
}
