package com.CustomerJwt.controller;

import com.CustomerJwt.dto.JwtResponse;
import com.CustomerJwt.dto.LoginRequest;
import com.CustomerJwt.dto.RegisterRequest;
import com.CustomerJwt.entity.Customer;
import com.CustomerJwt.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/auth")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(customerService.registerCustomer(request));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(customerService.loginCustomer(request));
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }
}