package com.avinash.practoappointmentbookingsystem.controllers;

import com.avinash.practoappointmentbookingsystem.dtos.SlotResponse;
import com.avinash.practoappointmentbookingsystem.services.SlotAvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/slots")
@RequiredArgsConstructor
public class SlotController {

    private final SlotAvailabilityService slotAvailabilityService;

    @GetMapping
    public ResponseEntity<?> getSlots(
            @RequestParam
            Long doctorLocationId,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date
    ){
        List<SlotResponse> slotResponses = slotAvailabilityService.getAvailableSlots(doctorLocationId,date);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(slotResponses);
    }
}
