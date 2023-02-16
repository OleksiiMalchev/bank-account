package com.bank.account.services.impl;

import com.bank.account.configuration.TestUnitConfig;
import com.bank.account.domain.Customer;
import com.bank.account.domain.dto.CustomerRespDTO;
import com.bank.account.repositories.CustomerRepository;
import com.bank.account.services.CustomerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CustomerServiceImplTest extends TestUnitConfig {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerRepository customerRepository;
    @Captor
    private ArgumentCaptor<Customer> customerArgumentCaptor;

    private final String testCustomerIdF = "06373673-3eca-413e-b58e-5844de327830";
    private final String testCustomerFirstNameF = "Oleksii";
    private final String testCustomerLastNameF = "Malchev";
    private final LocalDate testCustomerDateOfBirthF = LocalDate.of(1988, 1, 13);
    private final boolean testCustomerActiveF = true;


    private final String testCustomerIdS = "06373589-3eca-413e-b58e-5844de327830";
    private final String testCustomerFirstNameS = "Andrii";
    private final String testCustomerLastNameS = "Malchev";
    private final LocalDate testCustomerDateOfBirthS = LocalDate.of(1984, 8, 05);
    private final boolean testCustomerActiveS = true;


    @Test
    void getCustomers() {
        Customer customerTestF = Customer.builder()
                .firstName(testCustomerFirstNameF)
                .lastName(testCustomerLastNameF)
                .dateOfBirth(testCustomerDateOfBirthF)
                .active(testCustomerActiveF).build();

        Customer customerTestS = Customer.builder()
                .firstName(testCustomerFirstNameS)
                .lastName(testCustomerLastNameS)
                .dateOfBirth(testCustomerDateOfBirthS)
                .active(testCustomerActiveS).build();

        when(customerRepository.findAll()).thenReturn(List.of(customerTestF,customerTestS));
        List<CustomerRespDTO> customers = customerService.getCustomers();
        verify(customerRepository,times(1)).findAll();
        Assertions.assertEquals(customers.size(),2);


    }

    @Test
    void createCustomer() {
    }

    @Test
    void updateCustomer() {
    }

    @Test
    void getCustomerById() {
    }

    @Test
    void deleteCustomer() {
    }
}