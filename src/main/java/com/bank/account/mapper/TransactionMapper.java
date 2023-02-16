package com.bank.account.mapper;

import com.bank.account.domain.Customer;
import com.bank.account.domain.Transaction;
import com.bank.account.domain.dto.CustomerRespDTO;
import com.bank.account.domain.dto.TransactionRespDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionMapper {
    public TransactionRespDTO transactionRespDTO(Transaction transaction) {
        return TransactionRespDTO.builder()
                .id(transaction.getId())
                .amount(transaction.getAmount())
                .transactionStatus(transaction.getTransactionStatus())
                .transaction_time(transaction.getTransaction_time())
                .build();
    }
}
