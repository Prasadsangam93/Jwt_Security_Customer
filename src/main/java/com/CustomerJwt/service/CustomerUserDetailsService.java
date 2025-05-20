package com.CustomerJwt.service;


import com.CustomerJwt.entity.Customer;
import com.CustomerJwt.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomerUserDetailsService implements UserDetailsService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByCustomerMail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new User(customer.getCustomerMail(), customer.getPassword(), new java.util.ArrayList<>());
    }
}
