package com.bank.account.domain.dto;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class TransactionRespDTO {
    private Long amount;
    private String id;
    private String transactionStatus;
    private LocalDateTime transaction_time;
}
