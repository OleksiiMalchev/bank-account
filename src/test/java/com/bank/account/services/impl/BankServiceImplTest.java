package com.bank.account.services.impl;

import com.bank.account.configuration.TestUnitConfig;
import com.bank.account.domain.Account;
import com.bank.account.domain.Customer;
import com.bank.account.domain.Transaction;
import com.bank.account.domain.dto.AccountReqDTO;
import com.bank.account.domain.dto.AccountRespDTO;
import com.bank.account.domain.dto.CustomerAccountRespDTO;
import com.bank.account.domain.dto.DepositWithdrawalDTO;
import com.bank.account.repositories.AccountRepository;
import com.bank.account.repositories.CustomerRepository;
import com.bank.account.repositories.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class BankServiceImplTest extends TestUnitConfig {
    @Autowired
    private BankServiceImpl bankService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Captor
    private ArgumentCaptor<Account> accountArgumentCaptor;

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
    void createAccount() {

        String accountId = "10373589-3eca-413e-b58e-5844de327830";
        String transactionIdF = "06373589-3eca-413e-b58e-5844de327830";
        String transactionStatus = "Done";

        Account accountF = Account.builder()
                .Id(accountId)
                .customerId(testCustomerIdF)
                .initialCredit(10L)
                .balance(100L)
                .build();

        AccountReqDTO accountReq = AccountReqDTO.builder()
                .customerId(testCustomerIdF)
                .initialCredit(10L)
                .build();

        Transaction transactionF = Transaction.builder()
                .id(transactionIdF)
                .amount(10L)
                .accountId(accountId)
                .transaction_time(LocalDateTime.now())
                .transactionStatus(transactionStatus)
                .build();

        when(accountRepository.save(ArgumentMatchers.any(Account.class))).thenReturn(accountF);
        when(transactionRepository.save(ArgumentMatchers.any(Transaction.class))).thenReturn(transactionF);
        Optional<AccountRespDTO> account = bankService.createAccount(accountReq);
        verify(accountRepository, times(1)).save(accountArgumentCaptor.capture());
        Account accountEntity = accountArgumentCaptor.getValue();
        Assertions.assertEquals(testCustomerIdF, accountEntity.getCustomerId());
        Assertions.assertEquals(10L, accountEntity.getBalance());
        Assertions.assertTrue(account.isPresent());
        Assertions.assertEquals(accountId, account.get().getAccountId());
        Assertions.assertEquals(100L, account.get().getBalance());
    }

    @Test
    void reportCustomers() {

        String transactionIdF = "06373589-3eca-413e-b58e-5844de327830";
        String transactionIdS = "06373590-3eca-413e-b58e-5844de327830";
        String accountId = "10373589-3eca-413e-b58e-5844de327830";
        String transactionStatus = "Done";

        Transaction transactionF = Transaction.builder()
                .id(transactionIdF)
                .amount(10L)
                .accountId(accountId)
                .transaction_time(LocalDateTime.now())
                .transactionStatus(transactionStatus)
                .build();

        Transaction transactionS = Transaction.builder()
                .id(transactionIdS)
                .amount(10L)
                .accountId(accountId)
                .transaction_time(LocalDateTime.now())
                .transactionStatus(transactionStatus)
                .build();


        Account accountF = Account.builder()
                .Id(accountId)
                .customerId(testCustomerIdF)
                .initialCredit(10L)
                .balance(100L)
                .transactions(List.of(transactionF, transactionS))
                .build();

        Customer customer = Customer.builder()
                .firstName(testCustomerFirstNameF)
                .lastName(testCustomerLastNameF)
                .active(testCustomerActiveF)
                .id(testCustomerIdF)
                .dateOfBirth(testCustomerDateOfBirthF)
                .accounts(List.of(accountF)).build();

        when(customerRepository.reportCustomer()).thenReturn(List.of(customer));
        List<CustomerAccountRespDTO> customerAccountRespDTOS = bankService.reportCustomers();
        verify(customerRepository, times(1)).reportCustomer();
        Assertions.assertEquals(customerAccountRespDTOS.size(), 1);
        Assertions.assertEquals(testCustomerFirstNameF, customerAccountRespDTOS.get(0).getFirstName());
        Assertions.assertEquals(testCustomerLastNameF, customerAccountRespDTOS.get(0).getLastName());
        Assertions.assertEquals(testCustomerIdF, customerAccountRespDTOS.get(0).getCustomerId());
        Assertions.assertEquals(testCustomerActiveF, customerAccountRespDTOS.get(0).getActive());
        Assertions.assertEquals(accountId, customerAccountRespDTOS.get(0).getAccount().get(0).getAccountId());
        Assertions.assertEquals(100L, customerAccountRespDTOS.get(0).getAccount().get(0).getBalance());
        Assertions.assertEquals(10L, customerAccountRespDTOS.get(0)
                .getAccount().get(0).getTransaction().get(0).getAmount());
        Assertions.assertEquals(transactionStatus, customerAccountRespDTOS.get(0)
                .getAccount().get(0).getTransaction().get(0).getTransactionStatus());
        Assertions.assertEquals(transactionIdF, customerAccountRespDTOS.get(0)
                .getAccount().get(0).getTransaction().get(0).getId());
    }

    @Test
    void reportCustomerById() {

        String transactionIdF = "06373589-3eca-413e-b58e-5844de327830";
        String transactionIdS = "06373590-3eca-413e-b58e-5844de327830";
        String accountId = "10373589-3eca-413e-b58e-5844de327830";
        String transactionStatus = "Done";

        Transaction transactionF = Transaction.builder()
                .id(transactionIdF)
                .amount(10L)
                .accountId(accountId)
                .transaction_time(LocalDateTime.now())
                .transactionStatus(transactionStatus)
                .build();

        Transaction transactionS = Transaction.builder()
                .id(transactionIdS)
                .amount(10L)
                .accountId(accountId)
                .transaction_time(LocalDateTime.now())
                .transactionStatus(transactionStatus)
                .build();


        Account accountF = Account.builder()
                .Id(accountId)
                .customerId(testCustomerIdF)
                .initialCredit(10L)
                .balance(100L)
                .transactions(List.of(transactionF, transactionS))
                .build();

        Customer customer = Customer.builder()
                .firstName(testCustomerFirstNameF)
                .lastName(testCustomerLastNameF)
                .active(testCustomerActiveF)
                .id(testCustomerIdF)
                .dateOfBirth(testCustomerDateOfBirthF)
                .accounts(List.of(accountF)).build();

        when(customerRepository.reportCustomerById(testCustomerIdF)).thenReturn(Optional.ofNullable(customer));
        Optional<CustomerAccountRespDTO> customerAccountRespDTO = bankService.reportCustomerById(testCustomerIdF);
        verify(customerRepository, times(1)).reportCustomerById(testCustomerIdF);
        Assertions.assertEquals(testCustomerFirstNameF, customerAccountRespDTO.get().getFirstName());
        Assertions.assertEquals(testCustomerLastNameF, customerAccountRespDTO.get().getLastName());
        Assertions.assertEquals(testCustomerIdF, customerAccountRespDTO.get().getCustomerId());
        Assertions.assertEquals(testCustomerActiveF, customerAccountRespDTO.get().getActive());
        Assertions.assertEquals(accountId, customerAccountRespDTO.get().getAccount().get(0).getAccountId());
        Assertions.assertEquals(100L, customerAccountRespDTO.get().getAccount().get(0).getBalance());
        Assertions.assertEquals(10L, customerAccountRespDTO.get()
                .getAccount().get(0).getTransaction().get(0).getAmount());
        Assertions.assertEquals(transactionStatus, customerAccountRespDTO.get()
                .getAccount().get(0).getTransaction().get(0).getTransactionStatus());
        Assertions.assertEquals(transactionIdF, customerAccountRespDTO.get()
                .getAccount().get(0).getTransaction().get(0).getId());
    }

    @Test
    void depositToAccount(){
        String accountId = "10373589-3eca-413e-b58e-5844de327830";
        String transactionIdF = "06373589-3eca-413e-b58e-5844de327830";
        String transactionStatus = "Done";

        Account accountF = Account.builder()
                .Id(accountId)
                .customerId(testCustomerIdF)
                .balance(100L)
                .build();

        Account accountDep = Account.builder()
                .Id(accountId)
                .customerId(testCustomerIdF)
                .balance(1100L)
                .build();
        DepositWithdrawalDTO deposit = DepositWithdrawalDTO.builder().amount(1000L).build();

        Long finalBalanceAfterDeposit = 1100L;

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(accountF));
        when(accountRepository.save(ArgumentMatchers.any(Account.class))).thenReturn(accountDep);
        Optional<AccountRespDTO> accountRespDTO = bankService.depositToAccount(accountId, deposit);
        verify(accountRepository, times(1)).findById(accountId);
        Assertions.assertEquals(finalBalanceAfterDeposit, accountRespDTO.get().getBalance());

    }

    @Test
    void withdrawalFromAccount() throws IOException {
        String accountId = "10373589-3eca-413e-b58e-5844de327830";
        String transactionIdF = "06373589-3eca-413e-b58e-5844de327830";
        String transactionStatus = "Done";

        Account accountF = Account.builder()
                .Id(accountId)
                .customerId(testCustomerIdF)
                .balance(1100L)
                .build();

        Account accountDep = Account.builder()
                .Id(accountId)
                .customerId(testCustomerIdF)
                .balance(100L)
                .build();

        DepositWithdrawalDTO withdrawal = DepositWithdrawalDTO.builder().amount(1000L).build();
        Long finalBalanceAfterDeposit = 100L;
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(accountF));
        when(accountRepository.save(ArgumentMatchers.any(Account.class))).thenReturn(accountDep);
        Optional<AccountRespDTO> accountRespDTO = bankService.withdrawalFromAccount(accountId, withdrawal);
        verify(accountRepository, times(1)).findById(accountId);
        Assertions.assertEquals(finalBalanceAfterDeposit, accountRespDTO.get().getBalance());

    }
}