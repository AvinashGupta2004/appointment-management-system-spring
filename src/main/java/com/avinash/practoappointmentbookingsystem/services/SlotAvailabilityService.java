package com.avinash.practoappointmentbookingsystem.services;

import com.avinash.practoappointmentbookingsystem.dtos.SlotResponse;
import com.avinash.practoappointmentbookingsystem.entities.doctor.Slot;
import com.avinash.practoappointmentbookingsystem.repositories.SlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SlotAvailabilityService {

    private final SlotRepository slotRepository;

    public List<SlotResponse> getAvailableSlots(Long doctorLocationId, LocalDate date) {
        List<Slot> slots = slotRepository.findByDoctorLocation_IdAndSlotDate(doctorLocationId, date);

        return slots.stream()
                .map(slot ->
                        SlotResponse.builder()
                                .slotId(slot.getId())
                                .startTime(slot.getStartTime())
                                .endTime(slot.getEndTime())
                                .slotStatus(slot.getStatus())
                                .build()
                        ).toList();

    }
}
