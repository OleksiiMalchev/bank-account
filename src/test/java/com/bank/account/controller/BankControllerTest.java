package com.bank.account.controller;

import com.bank.account.Application;
import com.bank.account.configuration.TestUnitConfig;
import com.bank.account.domain.Transaction;
import com.bank.account.domain.dto.*;
import com.bank.account.services.BankService;
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
class BankControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BankService bankService;
    @Autowired
    private ObjectMapper objectMapper;
    private final String testCustomerIdF = "06373673-3eca-413e-b58e-5844de327830";
    private final String testCustomerFirstNameF = "Oleksii";
    private final String testCustomerLastNameF = "Malchev";
    private final LocalDate testCustomerDateOfBirthF = LocalDate.of(1988, 1, 13);
    private final boolean testCustomerActiveF = true;
    private final String accountId = "16373673-3eca-413e-b58e-5844de327830";
    private final Long testBalance = 100L;

    @Test
    void reportCustomers() throws Exception {

        TransactionRespDTO transaction = TransactionRespDTO.builder().build();

        AccountTransactionRespDTO transactionsAccount = AccountTransactionRespDTO.builder()
                .accountId(accountId)
                .balance(testBalance)
                .transaction(List.of(transaction))
                .build();

        CustomerAccountRespDTO reportCustomers = CustomerAccountRespDTO.builder().customerId(testCustomerIdF)
                .firstName(testCustomerFirstNameF)
                .lastName(testCustomerLastNameF)
                .active(testCustomerActiveF)
                .account(List.of(transactionsAccount))
                .customerId(testCustomerIdF).build();

        Mockito.when(bankService.reportCustomers()).thenReturn(List.of(reportCustomers));
        mockMvc.perform(MockMvcRequestBuilders.get("/bank/customers"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value(testCustomerFirstNameF))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName").value(testCustomerLastNameF))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].active").value(testCustomerActiveF))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].active").value(testCustomerActiveF))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].account[0].accountId").value(accountId))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].account[0].balance").value(testBalance))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].account[0].transaction[0]").exists());

    }

    @Test
    void reportCustomerById() throws Exception {

        TransactionRespDTO transaction = TransactionRespDTO.builder().build();

        AccountTransactionRespDTO transactionsAccount = AccountTransactionRespDTO.builder()
                .accountId(accountId)
                .balance(testBalance)
                .transaction(List.of(transaction))
                .build();

        CustomerAccountRespDTO reportCustomers = CustomerAccountRespDTO.builder().customerId(testCustomerIdF)
                .firstName(testCustomerFirstNameF)
                .lastName(testCustomerLastNameF)
                .active(testCustomerActiveF)
                .account(List.of(transactionsAccount))
                .customerId(testCustomerIdF).build();

        Mockito.when(bankService.reportCustomerById(testCustomerIdF)).thenReturn(Optional.ofNullable(reportCustomers));
        mockMvc.perform(MockMvcRequestBuilders.get("/bank/customers/{id}", testCustomerIdF))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(testCustomerFirstNameF))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(testCustomerLastNameF))
                .andExpect(MockMvcResultMatchers.jsonPath("$.active").value(testCustomerActiveF))
                .andExpect(MockMvcResultMatchers.jsonPath("$.active").value(testCustomerActiveF))
                .andExpect(MockMvcResultMatchers.jsonPath("$.account[0].accountId").value(accountId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.account[0].balance").value(testBalance))
                .andExpect(MockMvcResultMatchers.jsonPath("$.account[0].transaction[0]").exists());
    }

    @Test
    void createAccount() throws Exception {

        AccountReqDTO accountReq = AccountReqDTO.builder().customerId(testCustomerIdF)
                .initialCredit(testBalance).build();
        AccountRespDTO accountRespDTO = AccountRespDTO.builder().accountId(accountId)
                .balance(testBalance).build();
        Mockito.when(bankService.createAccount(Mockito.any(AccountReqDTO.class)))
                .thenReturn(Optional.of(accountRespDTO));
        String valueAsString = objectMapper.writeValueAsString(accountReq);
        mockMvc.perform(MockMvcRequestBuilders.post("/bank/customers/add-account").content(valueAsString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(201))
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountId").value(accountId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(testBalance));
    }

    @Test
    void depositToAccount() throws Exception {
        Long deposit = 100L;
        DepositWithdrawalDTO depositDTO = DepositWithdrawalDTO.builder().amount(deposit).build();
        AccountRespDTO accountRespDTO = AccountRespDTO.builder().accountId(accountId).balance(testBalance).build();

        Mockito.when(bankService.depositToAccount(Mockito.any(),Mockito.any(DepositWithdrawalDTO.class)))
                .thenReturn(Optional.of(accountRespDTO));
        String valueAsString = objectMapper.writeValueAsString(depositDTO);
        mockMvc.perform(MockMvcRequestBuilders.put("/bank/customers/deposit/{id}",accountId)
                        .content(valueAsString).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountId").value(accountId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(testBalance));
    }

    @Test
    void withdrawalFromAccount() throws Exception {
        Long withdrawal = 100L;
        Long balanceafterwithdrawal = 0L;
        DepositWithdrawalDTO depositDTO = DepositWithdrawalDTO.builder().amount(withdrawal).build();
        AccountRespDTO accountRespDTO = AccountRespDTO.builder().accountId(accountId).balance(balanceafterwithdrawal).build();

        Mockito.when(bankService.withdrawalFromAccount(Mockito.any(),Mockito.any(DepositWithdrawalDTO.class)))
                .thenReturn(Optional.of(accountRespDTO));
        String valueAsString = objectMapper.writeValueAsString(depositDTO);
        mockMvc.perform(MockMvcRequestBuilders.put("/bank/customers/withdrawal/{id}",accountId)
                        .content(valueAsString).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountId").value(accountId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(0L));
    }
}