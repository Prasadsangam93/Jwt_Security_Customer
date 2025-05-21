package com.CustomerJwt.controller;

import com.CustomerJwt.dto.JwtResponse;
import com.CustomerJwt.dto.LoginRequest;
import com.CustomerJwt.dto.RegisterRequest;
import com.CustomerJwt.entity.Customer;
import com.CustomerJwt.service.CustomerService;
import com.CustomerJwt.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(customerService.registerCustomer(request));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {
        JwtResponse jwtResponse = customerService.loginCustomer(request);
        return ResponseEntity.ok(jwtResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAll")
    public List<Customer> getAll() {
        return customerService.getAllCustomers();
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/{id}")
    public Customer getById(@PathVariable Long id) {
        return customerService.getByCustomerId(id);

}

    @PostMapping("/refresh-token")
    public ResponseEntity<JwtResponse> refreshToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");

        if (refreshToken == null || refreshToken.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            String username = jwtUtil.extractUsername(refreshToken);

            if (jwtUtil.isTokenExpired(refreshToken)) {
                return ResponseEntity.status(401).body(null);
            }

            String newAccessToken = jwtUtil.generateToken(username);
            String newRefreshToken = jwtUtil.generateRefreshToken(username);

            return ResponseEntity.ok(new JwtResponse(newAccessToken, newRefreshToken));
        } catch (Exception e) {
            return ResponseEntity.status(403).build();
        }
    }
}
