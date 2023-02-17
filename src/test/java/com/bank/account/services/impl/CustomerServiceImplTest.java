package com.bank.account.services.impl;

import com.bank.account.configuration.TestUnitConfig;
import com.bank.account.domain.Customer;
import com.bank.account.domain.dto.CustomerReqDTO;
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
import java.util.Optional;

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

        when(customerRepository.findAll()).thenReturn(List.of(customerTestF, customerTestS));
        List<CustomerRespDTO> customers = customerService.getCustomers();
        verify(customerRepository, times(1)).findAll();
        Assertions.assertEquals(customers.size(), 2);
        Assertions.assertEquals(testCustomerFirstNameF, customers.get(0).getFirstName());
        Assertions.assertEquals(testCustomerLastNameF, customers.get(0).getLastName());
        Assertions.assertEquals(testCustomerDateOfBirthF, customers.get(0).getDateOfBirth());
        Assertions.assertEquals(testCustomerActiveF, customers.get(0).isActive());
        Assertions.assertEquals(testCustomerFirstNameS, customers.get(1).getFirstName());
        Assertions.assertEquals(testCustomerLastNameS, customers.get(1).getLastName());
        Assertions.assertEquals(testCustomerDateOfBirthS, customers.get(1).getDateOfBirth());
        Assertions.assertEquals(testCustomerActiveS, customers.get(1).isActive());
    }

    @Test
    void createCustomer() {

        CustomerReqDTO customerReqDTO = CustomerReqDTO.builder()
                .firstName(testCustomerFirstNameF)
                .lastName(testCustomerLastNameF)
                .dateOfBirth(testCustomerDateOfBirthF)
                .active(testCustomerActiveF)
                .build();

        Customer customerSave = Customer.builder().firstName(testCustomerFirstNameF)
                .lastName(testCustomerLastNameF)
                .dateOfBirth(testCustomerDateOfBirthF)
                .active(testCustomerActiveF)
                .id(testCustomerIdF)
                .build();

        when(customerRepository.save(any(Customer.class))).thenReturn(customerSave);
        Optional<CustomerRespDTO> customerRespDTO = customerService.createCustomer(customerReqDTO);
        verify(customerRepository, times(1)).save(customerArgumentCaptor.capture());
        Customer customerEntity = customerArgumentCaptor.getValue();
        Assertions.assertEquals(testCustomerFirstNameF, customerEntity.getFirstName());
        Assertions.assertEquals(testCustomerLastNameF, customerEntity.getLastName());
        Assertions.assertEquals(testCustomerDateOfBirthF, customerEntity.getDateOfBirth());
        Assertions.assertEquals(testCustomerActiveF, customerEntity.isActive());
        Assertions.assertTrue(customerRespDTO.isPresent());
        Assertions.assertEquals(testCustomerFirstNameF, customerRespDTO.get().getFirstName());
        Assertions.assertEquals(testCustomerLastNameF, customerRespDTO.get().getLastName());
        Assertions.assertEquals(testCustomerDateOfBirthF, customerRespDTO.get().getDateOfBirth());
        Assertions.assertEquals(testCustomerActiveF, customerRespDTO.get().isActive());

    }

    @Test
    void updateCustomer() {

        CustomerReqDTO customerReqDTO = CustomerReqDTO.builder()
                .firstName(testCustomerFirstNameS)
                .lastName(testCustomerLastNameS)
                .dateOfBirth(testCustomerDateOfBirthS)
                .active(testCustomerActiveS)
                .build();

        Customer customerInBase = Customer.builder().firstName(testCustomerFirstNameF)
                .lastName(testCustomerLastNameF)
                .dateOfBirth(testCustomerDateOfBirthF)
                .active(testCustomerActiveF)
                .id(testCustomerIdF)
                .build();

        Customer customerUpdate = Customer.builder().firstName(testCustomerFirstNameF)
                .lastName(testCustomerLastNameS)
                .dateOfBirth(testCustomerDateOfBirthS)
                .active(testCustomerActiveS)
                .id(testCustomerIdF)
                .build();

        when(customerRepository.existsById(testCustomerIdF)).thenReturn(true);
        when(customerRepository.findById(testCustomerIdF)).thenReturn(Optional.ofNullable(customerInBase));
        when(customerRepository.save(customerUpdate)).thenReturn(customerUpdate);
        when(customerRepository.findById(testCustomerIdF)).thenReturn(Optional.ofNullable(customerUpdate));
        Optional<CustomerRespDTO> customerRespDTO = customerService.updateCustomer(testCustomerIdF, customerReqDTO);
        Assertions.assertTrue(customerRespDTO.isPresent());
        Assertions.assertEquals(testCustomerFirstNameS, customerRespDTO.get().getFirstName());
        Assertions.assertEquals(testCustomerLastNameS, customerRespDTO.get().getLastName());
        Assertions.assertEquals(testCustomerDateOfBirthS, customerRespDTO.get().getDateOfBirth());
        Assertions.assertEquals(testCustomerActiveS, customerRespDTO.get().isActive());
    }

    @Test
    void getCustomerById() {

        Customer customer = Customer.builder().firstName(testCustomerFirstNameF)
                .lastName(testCustomerLastNameF)
                .dateOfBirth(testCustomerDateOfBirthF)
                .active(testCustomerActiveF)
                .id(testCustomerIdF)
                .build();

        when(customerRepository.findById(testCustomerIdF)).thenReturn(Optional.of(customer));
        Optional<CustomerRespDTO> customerById = customerService.getCustomerById(testCustomerIdF);
        verify(customerRepository,times(1)).findById(testCustomerIdF);
        Assertions.assertTrue(customerById.isPresent());
        Assertions.assertEquals(testCustomerFirstNameF, customerById.get().getFirstName());
        Assertions.assertEquals(testCustomerLastNameF, customerById.get().getLastName());
        Assertions.assertEquals(testCustomerDateOfBirthF, customerById.get().getDateOfBirth());
        Assertions.assertEquals(testCustomerActiveF, customerById.get().isActive());
    }

    @Test
    void deleteCustomer() {

        Customer customer = Customer.builder().firstName(testCustomerFirstNameF)
                .lastName(testCustomerLastNameF)
                .dateOfBirth(testCustomerDateOfBirthF)
                .active(testCustomerActiveF)
                .id(testCustomerIdF)
                .build();

        when(customerRepository.existsById(customer.getId())).thenReturn(true);
        Assertions.assertTrue(customerService.deleteCustomer(customer.getId()));
        when(customerRepository.existsById(customer.getId())).thenReturn(false);
        Assertions.assertFalse(customerService.deleteCustomer(customer.getId()));
    }
}