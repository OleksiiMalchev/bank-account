package com.bank.account.domain.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepositWithdrawalDTO {
    private Long amount;
}
