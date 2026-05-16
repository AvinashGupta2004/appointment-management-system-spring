package com.avinash.practoappointmentbookingsystem.dtos;

import com.avinash.practoappointmentbookingsystem.enums.SpecializationType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Data
@Validated
public class DoctorRegistrationRequest {
    @NotBlank
    private String fullName;

    @NotBlank
    private String doctorRegistrationId;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @Length(max = 10)
    private String mobile;

    @Enumerated(EnumType.STRING)
    @NotNull
    private SpecializationType specialization;

    @NotBlank
    private String qualification;

    @Max(value = 100)
    @Min(value = 0)
    private Integer experienceYears;

    @NotNull
    @Min(value = 0)
    private double consultationFees;
}
