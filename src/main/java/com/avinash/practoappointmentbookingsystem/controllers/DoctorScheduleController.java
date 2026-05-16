package com.avinash.practoappointmentbookingsystem.controllers;

import com.avinash.practoappointmentbookingsystem.dtos.DoctorScheduleRequest;
import com.avinash.practoappointmentbookingsystem.entities.doctor.DoctorSchedule;
import com.avinash.practoappointmentbookingsystem.services.DoctorScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class DoctorScheduleController {

    private final DoctorScheduleService doctorScheduleService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<?> createDoctorSchedule(
            @Valid
            @RequestBody
            DoctorScheduleRequest request
    ) {
        Long scheduleId =
                doctorScheduleService
                        .createDoctorSchedule(request);

        return ResponseEntity.ok(
                "Doctor schedule created successfully with ID: "
                        + scheduleId
        );
    }
}
