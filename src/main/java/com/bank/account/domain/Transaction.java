package com.bank.account.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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
    @Column(name="transaction_status")
    private String transactionStatus;
    @Column(name = "transaction_time")
    private LocalDateTime transaction_time;
    @ManyToOne
    @JoinColumn(name="author_id",insertable = false, updatable = false)
    private Account account;
}
