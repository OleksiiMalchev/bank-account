package com.bank.account.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Table(name="account")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String Id;
    @Column(name="customer_id")
    private String customerId;
    @Column(name="initial_credit")
    private Long initialCredit;
    @Column(name = "balance")
    private Long balance;
    @ManyToOne
    @JoinColumn(name="customer_id",insertable = false, updatable = false)
    private Customer customer;
    @OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Transaction> transactions;
}
