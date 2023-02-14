package com.bank.account.controller;

import com.bank.account.domain.dto.CustomerRespDTO;
import com.bank.account.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/management/customers")
    public ResponseEntity<? super List<CustomerRespDTO>> getAllCustomer()  {
        List<CustomerRespDTO> customers = customerService.getCustomers();
        if (customers.isEmpty()) {
            return new ResponseEntity<>("Customers not found ", HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.status(200).body(customers);
        }
    }
    @GetMapping("/management/customers/{id}")
    public ResponseEntity<? super CustomerRespDTO> getCustomerById(@PathVariable("id") String customerId) {
       return null;
    }
}
