package com.bank.account.services.impl;

import com.bank.account.domain.Account;
import com.bank.account.domain.Customer;
import com.bank.account.domain.Transaction;
import com.bank.account.domain.dto.AccountReqDTO;
import com.bank.account.domain.dto.AccountRespDTO;
import com.bank.account.domain.dto.CustomerAccountRespDTO;
import com.bank.account.domain.dto.DepositWithdrawalDTO;
import com.bank.account.mapper.AccountMapper;
import com.bank.account.mapper.CustomerMapper;
import com.bank.account.repositories.AccountRepository;
import com.bank.account.repositories.CustomerRepository;
import com.bank.account.repositories.TransactionRepository;
import com.bank.account.services.BankService;
import jakarta.transaction.Transactional;
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
    private final CustomerMapper customerMapper;

    @Transactional
    @Override
    public Optional<AccountRespDTO> createAccount(AccountReqDTO accountReqDTO) {
        Optional<AccountRespDTO> accountRespDTO = accountMapper.reqDTOAccount(accountReqDTO)
                .map(accountRepository::save)
                .map(accountMapper::accountRespDTO);
        if (accountReqDTO.getInitialCredit() != 0) {
            Transaction transaction = Transaction.builder()
                    .transaction_time(LocalDateTime.now())
                    .amount(accountReqDTO.getInitialCredit())
                    .transactionStatus("Done")
                    .accountId(accountRespDTO.get().getAccountId()).build();
            transactionRepository.save(transaction);
        }
        return accountRespDTO;
    }

    @Override
    public List<CustomerAccountRespDTO> reportCustomers() {
        return customerRepository.reportCustomer()
                .stream().map(customerMapper::customerAccountRespDTO)
                .toList();
    }

    @Override
    public Optional<CustomerAccountRespDTO> reportCustomerById(String customerId) {
        return customerRepository.reportCustomerById(customerId)
                .map(customerMapper::customerAccountRespDTO);
    }

    @Override
    public Optional<AccountRespDTO> depositToAccount(String accountId, DepositWithdrawalDTO deposit) {
        Optional<Account> accountInBase = accountRepository.findById(accountId);
        if (deposit.getAmount() >= 0) {
            Transaction transaction = Transaction.builder()
                    .transaction_time(LocalDateTime.now())
                    .amount(deposit.getAmount())
                    .transactionStatus("Done")
                    .accountId(accountId).build();
            transactionRepository.save(transaction);
            Long balance = accountInBase.get().getBalance();
            accountInBase.get().setBalance(balance + deposit.getAmount());
        }
        return accountInBase.map(accountRepository::save)
                .map(accountMapper::accountRespDTO);
    }

    @Override
    public Optional<AccountRespDTO> withdrawalFromAccount(String accountId, DepositWithdrawalDTO withdrawal) {
        Optional<Account> accountInBase = accountRepository.findById(accountId);
        Long balance = accountInBase.get().getBalance();
        if(withdrawal.getAmount() >=0 && withdrawal.getAmount()<=balance){
            Transaction transaction = Transaction.builder()
                    .transaction_time(LocalDateTime.now())
                    .amount(withdrawal.getAmount())
                    .transactionStatus("Done")
                    .accountId(accountId).build();
            transactionRepository.save(transaction);
            accountInBase.get().setBalance(balance - withdrawal.getAmount());
        }
        return accountInBase.map(accountRepository::save)
                .map(accountMapper::accountRespDTO);
    }
}
