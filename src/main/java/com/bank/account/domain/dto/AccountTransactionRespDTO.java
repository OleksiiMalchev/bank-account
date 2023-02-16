package com.bank.account.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@Builder
public class AccountTransactionRespDTO {
    private String accountId;
    private Long balance;
    private List<TransactionRespDTO> transaction;
}
