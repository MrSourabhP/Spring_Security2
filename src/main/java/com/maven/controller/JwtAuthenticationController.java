package com.maven.controller;

import com.maven.model.*;
import com.maven.model.dtos.CustomerDtos;
import com.maven.model.dtos.JwtRequest;
import com.maven.model.dtos.JwtResponse;
import com.maven.repository.CustomerRepository;
import com.maven.service.CustomerService;
import com.maven.service.jwt.AdminServiceImpl;
import com.maven.utils.JwtUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/service")
@CrossOrigin("*")
public class JwtAuthenticationController {
    @Autowired
    public CustomerService customerService;
    @Autowired
    public CustomerRepository customerRepository;
    @Autowired
    public AdminServiceImpl adminService;
    @Autowired
    public AuthenticationManager authenticationManager;
    @Autowired
    public JwtUtil jwtUtils;

    @PostConstruct
    public void createSuperAdmin(){
        Customer c = new Customer();
        c.setId(1L);
        c.setUsername("admin25@gmail.com");
        c.setContact("8636475683");
        c.setPassword(new BCryptPasswordEncoder().encode("sou01pat2000"));
        c.setRole("SUPER_ADMIN");
        c.setEnabled(Boolean.TRUE);
        customerService.createCustomer(c);
    }

    @PostMapping("/create-customer")
    public Customer createCustomer(@RequestBody Customer customer){
        customer.setRole("CUSTOMER");
        customer.setEnabled(Boolean.TRUE);
        return customerService.createCustomer(customer);
    }

    @PostMapping("/login")
    public JwtResponse createAuthenticationToken(@RequestBody JwtRequest request){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (BadCredentialsException e){
            throw new BadCredentialsException("Incorrect Username or Password");
        }
        UserDetails userDetails = adminService.loadUserByUsername(request.getUsername());

        Optional<Customer> optionalCustomer = customerRepository.getCustomerByUsername(userDetails.getUsername());

        final String jwt = jwtUtils.generateToken(userDetails.getUsername());
        JwtResponse response = new JwtResponse();
        if (optionalCustomer.isPresent()){
            response.setToken(jwt);
            response.setRole(optionalCustomer.get().getRole());
            response.setUsername(optionalCustomer.get().getUsername());
            return response;
        }
        return null;

    }
    @GetMapping("/getCustomerById/{id}")
    public CustomerDtos getCustomerById(@PathVariable("id") Long id){
        Customer customerById = customerService.getCustomerById(id);
        CustomerDtos c = new CustomerDtos();
        c.setId(customerById.getId());
        c.setUsername(customerById.getUsername());
        c.setContact(customerById.getContact());
        c.setPassword(new BCryptPasswordEncoder().encode(customerById.getPassword()));
        c.setImage(customerById.getImage());
        return c;
    }
    @GetMapping("/get-all-customers")
    public List<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }
    @DeleteMapping("/delete-customer/{id}")
    public Boolean deleteCustomer(@PathVariable("id") Long id){
        customerService.deleteCustomer(id);
        return true;
    }
    @PutMapping("/update-customer")
    public Customer updateCustomer(
            @RequestParam("id") Long id,
            @RequestParam("username") String username,
                                   @RequestParam("contact") String contact,
                                   @RequestParam("password") String password,
                                   @RequestParam("image")MultipartFile file) throws IOException {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setUsername(username);
        customer.setContact(contact);
        customer.setPassword(new BCryptPasswordEncoder().encode(password));
        customer.setImage(file.getBytes());
        return customerService.updatateCustomer(customer);
    }
    @PostMapping("/create-customer-with-multi")
    public Customer createCustomerWithMulti(
            @RequestParam("username") String username,
            @RequestParam("contact") String contact,
            @RequestParam("password") String password,
            @RequestParam("image")MultipartFile file
            ) throws IOException {
           Customer c1 = new Customer();
           c1.setUsername(username);
           c1.setContact(contact);
           c1.setPassword(new BCryptPasswordEncoder().encode(password));
           c1.setEnabled(Boolean.TRUE);
           c1.setRole("NORMAL");
           c1.setImage(file.getBytes());
           return customerService.createCustomer(c1);
    }
}
