package com.avinash.practoappointmentbookingsystem.dtos;

import com.avinash.practoappointmentbookingsystem.enums.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AppointmentRequest {

    private Long slotId;
    private Long userId;
    private String patientName;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String mobileNumber;
    private String email;
}
