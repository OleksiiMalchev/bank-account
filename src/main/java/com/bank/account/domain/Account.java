//package com.bank.account.domain;
//
//import jakarta.persistence.*;
//import lombok.*;
//import org.springframework.data.annotation.Id;
//
//import java.util.List;
//
//@Entity
//@Getter
//@Setter
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class Account {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private String id;
//    @Column(name="customer_id")
//    private Long customerId;
//    @Column(name="initial_credit")
//    private Long initialCredit;
//    @ManyToOne
//    @JoinColumn(name="customer_id",insertable = false, updatable = false)
//    private Customer customer;
//}
