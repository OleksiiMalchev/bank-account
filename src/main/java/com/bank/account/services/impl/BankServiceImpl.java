package com.bank.account.services.impl;

import com.bank.account.domain.Account;
import com.bank.account.domain.Customer;
import com.bank.account.domain.Transaction;
import com.bank.account.domain.dto.AccountReqDTO;
import com.bank.account.domain.dto.AccountRespDTO;
import com.bank.account.domain.dto.CustomerAccountRespDTO;
import com.bank.account.mapper.AccountMapper;
import com.bank.account.repositories.AccountRepository;
import com.bank.account.repositories.CustomerRepository;
import com.bank.account.repositories.TransactionRepository;
import com.bank.account.services.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BankServiceImpl implements BankService {
    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public Optional<AccountRespDTO> createAccount(AccountReqDTO accountReqDTO) {
        Optional<Account> account = accountMapper.reqDTOAccount(accountReqDTO);
        if(accountReqDTO.getInitialCredit()!=0){
            Transaction transaction = Transaction.builder()
                    .transaction_time(LocalDateTime.now())
                    .amount(accountReqDTO.getInitialCredit())
                    .transactionStatus("Done")
                    .account(account.get()).build();
            transactionRepository.save(transaction);
        }
        return account
                .map(accountRepository::save)
                .map(accountMapper::accountRespDTO);
    }

    @Override
    public List<Customer> reportCustomers() {
        return  customerRepository.findAll();
    }

    @Override
    public CustomerAccountRespDTO reportCustomerById(String customerId) {

        return null;
    }
}
