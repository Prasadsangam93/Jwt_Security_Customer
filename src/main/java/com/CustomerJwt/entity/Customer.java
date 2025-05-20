package com.CustomerJwt.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer_jwt")
@Entity
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
    }




