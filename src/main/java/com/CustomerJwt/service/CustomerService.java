package com.CustomerJwt.service;

import com.CustomerJwt.dto.*;
import com.CustomerJwt.entity.Customer;

import java.util.List;

public interface CustomerService {

    String registerCustomer(RegisterRequest request);
    JwtResponse loginCustomer(LoginRequest request);

    List<Customer> getAllCustomers();
}
