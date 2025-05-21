package com.CustomerJwt.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer_jwt")
public class Customer {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long customerId;

        private String customerName;

        @Column(unique = true, nullable = false)
        private String customerMail;

        @JsonIgnore
        @Column(nullable = false)
        private String password;

        @Column(nullable = false)
        private String role; // Example: ROLE_USER or ROLE_ADMIN
}
