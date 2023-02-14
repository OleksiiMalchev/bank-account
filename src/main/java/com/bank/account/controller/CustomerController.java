package com.bank.account.controller;

import com.bank.account.domain.dto.CustomerReqDTO;
import com.bank.account.domain.dto.CustomerRespDTO;
import com.bank.account.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/management/customers")
    public ResponseEntity<? super List<CustomerRespDTO>> getAllCustomer() {
        List<CustomerRespDTO> customers = customerService.getCustomers();
        if (customers.isEmpty()) {
            return new ResponseEntity<>("Customers not found ", HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.status(200).body(customers);
        }
    }

    @GetMapping("/management/customers/{id}")
    public ResponseEntity<? super CustomerRespDTO> getCustomerById(@PathVariable("id") String customerId) {
        Optional<CustomerRespDTO> customerById = customerService.getCustomerById(customerId);
        if (customerById.isPresent()) {
            return ResponseEntity.status(200).body(customerById);
        }
        return new ResponseEntity<>("Customer by id" + customerId + " not found. No action taken.", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/management/customers")
    public ResponseEntity<? super CustomerRespDTO> createCustomer(@RequestBody(required = false) CustomerReqDTO customerReqDTO) {
        Optional<CustomerRespDTO> customer = customerService.createCustomer(customerReqDTO);
        if (customer.isPresent()) {
            return ResponseEntity.status(201).body(customer);
        }
        return new ResponseEntity<>("Customer not create. No action taken.", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/management/customers")
    public ResponseEntity<? super CustomerRespDTO> updateCustomer(@PathVariable("id") String customerId,
                                                          @RequestBody(required = false) CustomerReqDTO customerReqDTO) {

        Optional<CustomerRespDTO> customerRespDTO = customerService.updateCustomer(customerId, customerReqDTO);
        if (customerRespDTO .isPresent()) {
            return ResponseEntity.status(200).body(customerRespDTO );
        }
        return new ResponseEntity<>("Customer not found. No action taken.", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/management/customers/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("id") String customerId)  {
        customerService.deleteCustomer(customerId);
        if (customerService.deleteCustomer(customerId)) {
            return new ResponseEntity<String>("DELETE Response", HttpStatus.OK);
        }
        return new ResponseEntity<>("Author not found. No action taken.", HttpStatus.NO_CONTENT);
    }

}
