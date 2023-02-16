package com.bank.account.mapper;

import com.bank.account.domain.Customer;
import com.bank.account.domain.dto.CustomerAccountRespDTO;
import com.bank.account.domain.dto.CustomerReqDTO;
import com.bank.account.domain.dto.CustomerRespDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomerMapper {
    private final AccountMapper accountMapper;
    public CustomerRespDTO customerRespDTO(Customer customer) {
        return CustomerRespDTO.builder()
                .active(customer.isActive())
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .dateOfBirth(customer.getDateOfBirth())
                .build();
    }
    public Optional<Customer> reqDTOCustomer(CustomerReqDTO customerReqDTO) {
        return Optional.of(customerReqDTO).map(c->Customer.builder()
                .active(c.isActive())
                .firstName(c.getFirstName())
                .lastName(c.getLastName())
                .dateOfBirth(c.getDateOfBirth())
                .build());
    }

    public CustomerAccountRespDTO customerAccountRespDTO(Customer customer) {
        return CustomerAccountRespDTO.builder()
                .active(customer.isActive())
                .customerId(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .account(customer.getAccounts().stream()
                        .map(accountMapper::accountTransactionRespDTO)
                        .toList())
                .build();
    }
}


