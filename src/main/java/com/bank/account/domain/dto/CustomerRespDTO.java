package com.bank.account.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.time.LocalDate;


@Getter
@Setter
@Builder
public class CustomerRespDTO {

    private boolean active;
    private String id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;

}
