package com.bank.account.controller;

import com.bank.account.domain.Customer;
import com.bank.account.domain.dto.*;
import com.bank.account.services.BankService;
import com.bank.account.services.CustomerService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor

public class BankController {
    private final BankService bankService;

    @GetMapping("/bank/customers")
    public ResponseEntity<? super List<CustomerAccountRespDTO>> getAllCustomer() {
        List<CustomerAccountRespDTO> customerAccountRespDTOS = bankService.reportCustomers();
        if (customerAccountRespDTOS.isEmpty()) {
            return new ResponseEntity<>("Customers not found ", HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.status(200).body(customerAccountRespDTOS);
        }
    }

    @GetMapping("/bank/customers/{id}")
    public ResponseEntity<? super CustomerAccountRespDTO> reportCustomerById(@PathVariable("id") String customerId) {
        Optional<CustomerAccountRespDTO> customerAccountRespDTO = bankService.reportCustomerById(customerId);
        if (customerAccountRespDTO.isPresent()) {
            return ResponseEntity.status(200).body(customerAccountRespDTO);
        }
        return new ResponseEntity<>("Customer by id" + customerId + " not found. No action taken.", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/bank/customers/add-account")
    public ResponseEntity<? super AccountRespDTO> createAccount(@RequestBody(required = false) AccountReqDTO accountReqDTO) {
        Optional<AccountRespDTO> account = bankService.createAccount(accountReqDTO);
        if (account.isPresent()) {
            return ResponseEntity.status(201).body(account);
        }
        return new ResponseEntity<>("Account not create. No action taken.", HttpStatus.NOT_FOUND);
    }
    @PutMapping("/bank/customers/deposit/{id}")
    public ResponseEntity<? super AccountRespDTO> depositToAccount(@PathVariable("id") String accountId,
                                                                   @RequestBody DepositWithdrawalDTO deposit) {
        Optional<AccountRespDTO> accountRespDTO = bankService.depositToAccount(accountId, deposit);
        if (accountRespDTO.isPresent()) {
            return ResponseEntity.status(200).body(accountRespDTO);
        }
        return new ResponseEntity<>("Transaction is faild. No action taken.", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/bank/customers/withdrawal/{id}")
    public ResponseEntity<? super AccountRespDTO> withdrawalFromAccount(@PathVariable("id") String accountId,
                                                                   @RequestBody DepositWithdrawalDTO withdrawal)
            throws IOException {
        Optional<AccountRespDTO> accountRespDTO = bankService.withdrawalFromAccount(accountId, withdrawal);
        if (accountRespDTO.isPresent()) {
            return ResponseEntity.status(200).body(accountRespDTO);
        }
        return new ResponseEntity<>("Transaction is faild. No action taken.", HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IOException.class)
    public String handleIOException(IOException exception) {
        return new String(exception.getMessage());
    }

}