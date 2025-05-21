package com.CustomerJwt.service;

import com.CustomerJwt.dto.JwtResponse;
import com.CustomerJwt.dto.LoginRequest;
import com.CustomerJwt.dto.RegisterRequest;
import com.CustomerJwt.entity.Customer;
import com.CustomerJwt.repository.CustomerRepository;
import com.CustomerJwt.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String registerCustomer(RegisterRequest request) {
        if (customerRepository.findByCustomerMail(request.getCustomerMail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        Customer customer = new Customer();
        customer.setCustomerName(request.getCustomerMail());
        customer.setCustomerMail(request.getCustomerMail());
        customer.setPassword(passwordEncoder.encode(request.getPassword()));
        customer.setRole(request.getRole());
        customerRepository.save(customer);
        return "Customer registered successfully";
    }

    @Override
    public JwtResponse loginCustomer(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getCustomerMail(),
                            request.getPassword()
                    )
            );
        } catch (Exception ex) {
            throw new RuntimeException("Invalid email or password");
        }

        UserDetails userDetails = customerUserDetailsService
                .loadUserByUsername(request.getCustomerMail());

        String accessToken = jwtUtil.generateToken(userDetails.getUsername());
        String refreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername());

        return new JwtResponse(accessToken, refreshToken);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getByCustomerId(Long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isPresent()){
            return customer.get();
        }else{

            return  null;
        }

    }
}
