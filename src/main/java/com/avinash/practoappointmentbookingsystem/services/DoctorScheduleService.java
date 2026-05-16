package com.avinash.practoappointmentbookingsystem.services;

import com.avinash.practoappointmentbookingsystem.dtos.DoctorScheduleRequest;
import com.avinash.practoappointmentbookingsystem.entities.doctor.Doctor;
import com.avinash.practoappointmentbookingsystem.entities.doctor.DoctorLocation;
import com.avinash.practoappointmentbookingsystem.entities.doctor.DoctorSchedule;
import com.avinash.practoappointmentbookingsystem.exceptions.DoctorLocationNotFoundException;
import com.avinash.practoappointmentbookingsystem.exceptions.DoctorNotFoundException;
import com.avinash.practoappointmentbookingsystem.repositories.DoctorLocationRepository;
import com.avinash.practoappointmentbookingsystem.repositories.DoctorRepository;
import com.avinash.practoappointmentbookingsystem.repositories.DoctorScheduleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorScheduleService {

    private final DoctorRepository doctorRepository;
    private final DoctorLocationRepository doctorLocationRepository;
    private final DoctorScheduleRepository doctorScheduleRepository;

    @Transactional
    public Long createDoctorSchedule(DoctorScheduleRequest request) {
        if (request.getValidTill() != null && request.getValidTill().isBefore(request.getValidFrom())) {
            throw new IllegalArgumentException("Valid Till cannot be before ValidFrom");
        }

        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found with given ID : " + request.getDoctorId()));

        DoctorLocation doctorLocation = doctorLocationRepository.findById(request.getDoctorLocationId())
                .orElseThrow(() -> new DoctorLocationNotFoundException("Doctor not found with given ID : " + request.getDoctorLocationId()));

        DoctorSchedule doctorSchedule = DoctorSchedule.builder()
                .doctor(doctor)
                .doctorLocation(doctorLocation)
                .serviceDay(request.getDayOfWeek())
                .slotDuration(request.getSlotDuration())
                .validFrom(request.getValidFrom())
                .validTill(request.getValidTill())
                .active(true)
                .build();

        doctorScheduleRepository.save(doctorSchedule);
        return doctorSchedule.getId();
    }
}
