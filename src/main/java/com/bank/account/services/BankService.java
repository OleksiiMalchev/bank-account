package com.bank.account.services;

import com.bank.account.domain.Customer;
import com.bank.account.domain.dto.AccountReqDTO;
import com.bank.account.domain.dto.AccountRespDTO;
import com.bank.account.domain.dto.CustomerAccountRespDTO;
import com.bank.account.domain.dto.DepositWithdrawalDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface BankService {
    Optional<AccountRespDTO> createAccount(AccountReqDTO accountReqDTO);

    List<CustomerAccountRespDTO> reportCustomers();

    Optional<CustomerAccountRespDTO> reportCustomerById(String customerId);
    Optional<AccountRespDTO> depositToAccount(String AccountId, DepositWithdrawalDTO deposit);
    Optional<AccountRespDTO> withdrawalFromAccount(String AccountId, DepositWithdrawalDTO withdrawal);

}
