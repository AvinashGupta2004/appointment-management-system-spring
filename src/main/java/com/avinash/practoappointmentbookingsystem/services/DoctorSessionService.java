package com.avinash.practoappointmentbookingsystem.services;

import com.avinash.practoappointmentbookingsystem.dtos.DoctorSessionRequest;
import com.avinash.practoappointmentbookingsystem.entities.doctor.DoctorSchedule;
import com.avinash.practoappointmentbookingsystem.entities.doctor.DoctorSession;
import com.avinash.practoappointmentbookingsystem.exceptions.DoctorScheduleNotFoundException;
import com.avinash.practoappointmentbookingsystem.repositories.DoctorScheduleRepository;
import com.avinash.practoappointmentbookingsystem.repositories.DoctorSessionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorSessionService {

    private final DoctorScheduleRepository doctorScheduleRepository;
    private final DoctorSessionRepository doctorSessionRepository;

    @Transactional
    public Long createDoctorSession(DoctorSessionRequest request) {
        if (!request.getEndTime().isAfter(request.getStartTime())) {
            throw new IllegalArgumentException("End time must be after Start time");
        }

        DoctorSchedule doctorSchedule = doctorScheduleRepository.findById(request.getDoctorScheduleId())
                .orElseThrow(() -> new DoctorScheduleNotFoundException("Doctor schedule not found!!"));

        boolean overlappingSessionExists = doctorSessionRepository
                .existsByDoctorSchedule_IdAndActiveAndStartTimeLessThanAndEndTimeGreaterThan(
                        request.getDoctorScheduleId(),
                        request.getEndTime(),
                        request.getStartTime()
                );

        if (overlappingSessionExists) {
            throw new IllegalArgumentException("Overlapping session already exists!");
        }

        DoctorSession doctorSession = DoctorSession.builder()
                .doctorSchedule(doctorSchedule)
                .sessionName(request.getSessionType())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .active(true)
                .build();

        doctorSessionRepository.save(doctorSession);
        return doctorSession.getId();
    }
}
