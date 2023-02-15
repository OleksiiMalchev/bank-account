package com.bank.account.mapper;

import com.bank.account.domain.Account;
import com.bank.account.domain.Customer;
import com.bank.account.domain.dto.AccountReqDTO;
import com.bank.account.domain.dto.AccountRespDTO;
import com.bank.account.domain.dto.CustomerReqDTO;
import com.bank.account.domain.dto.CustomerRespDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountMapper {
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
}
