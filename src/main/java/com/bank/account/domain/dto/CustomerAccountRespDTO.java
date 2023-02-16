package com.bank.account.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CustomerAccountRespDTO {
    private boolean active;
    private String customerId;
    private String firstName;
    private String lastName;
    List<AccountTransactionRespDTO> account;

}
