package com.bank.account.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "date_of_birth")
    private LocalDateTime dateOfBirth;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Transaction> transactions;

}
