package com.avinash.practoappointmentbookingsystem.controllers;

import com.avinash.practoappointmentbookingsystem.dtos.DoctorSessionRequest;
import com.avinash.practoappointmentbookingsystem.services.DoctorSessionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/doctor-sessions")
@RequiredArgsConstructor
public class DoctorSessionController {

    private final DoctorSessionService
            doctorSessionService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<?> createDoctorSession(
            @Valid
            @RequestBody
            DoctorSessionRequest request
    ) {

        Long sessionId = doctorSessionService
                .createDoctorSession(request);

        return ResponseEntity.ok("Doctor session created successfully with ID: " + sessionId);
    }
}