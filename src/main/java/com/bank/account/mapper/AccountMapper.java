package com.bank.account.mapper;

import com.bank.account.domain.Account;
import com.bank.account.domain.Customer;
import com.bank.account.domain.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountMapper {

    private final TransactionMapper transactionMapper;
    public Optional<Account> reqDTOAccount(AccountReqDTO accountReqDTO) {
        return Optional.of(accountReqDTO)
                .map(a -> Account.builder()
                        .customerId(a.getCustomerId())
                        .initialCredit(a.getInitialCredit())
                        .balance(a.getInitialCredit())
                        .build());
    }

    public AccountRespDTO accountRespDTO(Account account) {
        return AccountRespDTO.builder()
                .accountId(account.getId())
                .balance(account.getBalance())
                .build();
    }
    public AccountTransactionRespDTO accountTransactionRespDTO(Account account) {
        return AccountTransactionRespDTO.builder()
                .accountId(account.getId())
                .balance(account.getBalance())
                .transaction(account.getTransactions().stream()
                        .map(transactionMapper::transactionRespDTO)
                        .toList())
                .build();
    }
}
