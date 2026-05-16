package com.avinash.practoappointmentbookingsystem.controllers;

import com.avinash.practoappointmentbookingsystem.dtos.DoctorRegistrationRequest;
import com.avinash.practoappointmentbookingsystem.services.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createDoctor(
            @Valid
            @RequestBody
            DoctorRegistrationRequest request
    ) {
        Long doctorId = doctorService.createDoctor(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Doctor created Successfully! Doctor ID : " + doctorId);
    }

    @GetMapping("/{doctor_id}")
    public ResponseEntity<?> getDoctor(@PathVariable Long doctor_id) {
        return ResponseEntity.ok(doctorService.getDoctor(doctor_id));
    }

    @DeleteMapping("/{doctorId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteDoctor(
            @PathVariable Long doctorId
    ) {

        doctorService.deleteDoctor(doctorId);

        return ResponseEntity.ok(
                "Doctor deleted successfully"
        );
    }
}
