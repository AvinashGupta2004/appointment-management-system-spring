package com.avinash.practoappointmentbookingsystem.controllers;

import com.avinash.practoappointmentbookingsystem.dtos.AppointmentRequest;
import com.avinash.practoappointmentbookingsystem.services.AppointmentBookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentBookingService appointmentBookingService;

    @PostMapping
    public ResponseEntity<?> bookNewAppointment(@Valid @RequestBody AppointmentRequest request) {
        Long appointmentId = appointmentBookingService.bookAppointment(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Appointment booked Successfully " + appointmentId);
    }
}
