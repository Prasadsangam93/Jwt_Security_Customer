package com.CustomerJwt.service;

import com.CustomerJwt.entity.Customer;
import com.CustomerJwt.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = repository.findByCustomerMail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // You can add more roles here if needed in the future
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(customer.getRole()));

        return new User(customer.getCustomerMail(), customer.getPassword(), authorities);
    }
}
