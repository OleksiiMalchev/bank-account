package com.bank.account.controller;

import com.bank.account.domain.Customer;
import com.bank.account.domain.dto.*;
import com.bank.account.services.BankService;
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
public class BankController {
    private final BankService bankService;
    @GetMapping("/bank/customers")
    public ResponseEntity<? super List<CustomerAccountRespDTO>> getAllCustomer() {
        List<Customer> customerAccountRespDTOS = bankService.reportCustomers();
        if (customerAccountRespDTOS.isEmpty()) {
            return new ResponseEntity<>("Customers not found ", HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.status(200).body(customerAccountRespDTOS);
        }
    }
    @PostMapping("/bank/customers/add-account")
    public ResponseEntity<? super AccountRespDTO> createAccount(@RequestBody(required = false)AccountReqDTO accountReqDTO) {
        Optional<AccountRespDTO> account = bankService.createAccount(accountReqDTO);
        if (account.isPresent()) {
            return ResponseEntity.status(201).body(account);
        }
        return new ResponseEntity<>("Account not create. No action taken.", HttpStatus.NOT_FOUND);
    }

}