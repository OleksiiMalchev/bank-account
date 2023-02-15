package com.bank.account.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Table(name="transaction")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name="amount")
    private Long amount;
    @Column(name="account_id")
    private String accountId;
    @Column(name="transaction_status")
    private String transactionStatus;
    @Column(name = "transaction_time")
    private LocalDateTime transaction_time;
    @ManyToOne
    @JoinColumn(name="account_id",insertable = false, updatable = false)
    private Account account;
}
