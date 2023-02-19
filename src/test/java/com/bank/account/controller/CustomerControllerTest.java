package com.bank.account.controller;

import com.bank.account.Application;
import com.bank.account.configuration.TestUnitConfig;
import com.bank.account.domain.Customer;
import com.bank.account.domain.dto.CustomerReqDTO;
import com.bank.account.domain.dto.CustomerRespDTO;
import com.bank.account.services.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@Import(TestUnitConfig.class)
class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CustomerService customerService;
    @Autowired
    private ObjectMapper objectMapper;

    private final String testCustomerIdF = "06373673-3eca-413e-b58e-5844de327830";
    private final String testCustomerFirstNameF = "Oleksii";
    private final String testCustomerLastNameF = "Malchev";
    private final LocalDate testCustomerDateOfBirthF = LocalDate.of(1988, 1, 13);
    private final boolean testCustomerActiveF = true;

    @Test
    void getAllCustomer() throws Exception {

        CustomerRespDTO customerF = CustomerRespDTO.builder().firstName(testCustomerFirstNameF)
                .lastName(testCustomerLastNameF).dateOfBirth(testCustomerDateOfBirthF).active(testCustomerActiveF)
                .id(testCustomerIdF).build();

        Mockito.when(customerService.getCustomers()).thenReturn(List.of(customerF));
        mockMvc.perform(MockMvcRequestBuilders.get("/management/customers"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value(testCustomerFirstNameF))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName").value(testCustomerLastNameF))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].dateOfBirth")
                        .value(testCustomerDateOfBirthF.format(DateTimeFormatter.ISO_DATE)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].active").value(testCustomerActiveF));
    }

    @Test
    void getCustomerById() throws Exception {

        CustomerRespDTO customerF = CustomerRespDTO.builder().firstName(testCustomerFirstNameF)
                .lastName(testCustomerLastNameF).dateOfBirth(testCustomerDateOfBirthF).active(testCustomerActiveF)
                .id(testCustomerIdF).build();

        Mockito.when(customerService.getCustomerById(testCustomerIdF)).thenReturn(Optional.of(customerF));
        mockMvc.perform(MockMvcRequestBuilders.get("/management/customers/{id}", testCustomerIdF))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(testCustomerFirstNameF))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(testCustomerLastNameF))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateOfBirth")
                        .value(testCustomerDateOfBirthF.format(DateTimeFormatter.ISO_DATE)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.active").value(testCustomerActiveF));
    }

    @Test
    void createCustomer() throws Exception {
        CustomerReqDTO customerReq = CustomerReqDTO.builder().firstName(testCustomerFirstNameF)
                .lastName(testCustomerLastNameF).dateOfBirth(testCustomerDateOfBirthF).active(testCustomerActiveF)
                .build();

        CustomerRespDTO customerF = CustomerRespDTO.builder().firstName(testCustomerFirstNameF)
                .lastName(testCustomerLastNameF).dateOfBirth(testCustomerDateOfBirthF).active(testCustomerActiveF)
                .id(testCustomerIdF).build();

        Mockito.when(customerService.createCustomer(Mockito.any(CustomerReqDTO.class))).thenReturn(Optional.of(customerF));
        String valueAsString = objectMapper.writeValueAsString(customerReq);
        mockMvc.perform(MockMvcRequestBuilders.post("/management/customers").content(valueAsString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(201))
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(testCustomerFirstNameF))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(testCustomerLastNameF))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateOfBirth")
                        .value(testCustomerDateOfBirthF.format(DateTimeFormatter.ISO_DATE)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.active").value(testCustomerActiveF));
    }

    @Test
    void update() throws Exception {

        CustomerReqDTO customerReq = CustomerReqDTO.builder().firstName(testCustomerFirstNameF)
                .lastName(testCustomerLastNameF).dateOfBirth(testCustomerDateOfBirthF).active(testCustomerActiveF)
                .build();

        CustomerRespDTO customerF = CustomerRespDTO.builder().firstName(testCustomerFirstNameF)
                .lastName(testCustomerLastNameF).dateOfBirth(testCustomerDateOfBirthF).active(testCustomerActiveF)
                .id(testCustomerIdF).build();

        Mockito.when(customerService.updateCustomer(Mockito.any(),Mockito.any(Map.class))).thenReturn(Optional.of(customerF));
        String valueAsString = objectMapper.writeValueAsString(customerReq);
        mockMvc.perform(MockMvcRequestBuilders.patch("/management/customers/{id}",testCustomerIdF)
                        .content(valueAsString).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(testCustomerFirstNameF))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(testCustomerLastNameF))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateOfBirth")
                        .value(testCustomerDateOfBirthF.format(DateTimeFormatter.ISO_DATE)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.active").value(testCustomerActiveF));
    }

    @Test
    void deleteCustomer() throws Exception {

        Mockito.when(customerService.deleteCustomer(testCustomerIdF)).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.delete("/management/customers/{id}", testCustomerIdF))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }
    @Test
    void deleteWhenNoCustomer() throws Exception {

        Mockito.when(customerService.deleteCustomer(testCustomerIdF)).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.delete("/management/customers/{id}", testCustomerIdF))
                .andExpect(MockMvcResultMatchers.status().is(204));
    }
}