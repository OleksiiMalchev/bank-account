package com.bank.account.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CustomerReqDTO {

    private boolean active;
    private String firstName;
    private String lastName;
    private LocalDateTime dateOfBirth;
}
