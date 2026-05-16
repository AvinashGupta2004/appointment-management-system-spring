package com.avinash.practoappointmentbookingsystem.services;

import com.avinash.practoappointmentbookingsystem.entities.doctor.DoctorLocation;
import com.avinash.practoappointmentbookingsystem.entities.doctor.DoctorSession;
import com.avinash.practoappointmentbookingsystem.entities.doctor.Slot;
import com.avinash.practoappointmentbookingsystem.enums.LeaveStatus;
import com.avinash.practoappointmentbookingsystem.enums.SlotStatus;
import com.avinash.practoappointmentbookingsystem.exceptions.DoctorLocationNotFoundException;
import com.avinash.practoappointmentbookingsystem.repositories.DoctorLeaveRepository;
import com.avinash.practoappointmentbookingsystem.repositories.DoctorLocationRepository;
import com.avinash.practoappointmentbookingsystem.repositories.SessionRepository;
import com.avinash.practoappointmentbookingsystem.repositories.SlotRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SlotGenerationService {

    private final DoctorLocationRepository doctorLocationRepository;
    private final SessionRepository sessionRepository;
    private final DoctorLeaveRepository doctorLeaveRepository;
    private final SlotRepository slotRepository;

    @Transactional
    public void generateSlotsForDateForLocation(Long doctorLocationId, LocalDate date) {
        DoctorLocation doctorLocation = doctorLocationRepository.findById(doctorLocationId)
                .orElseThrow(() -> new DoctorLocationNotFoundException("Doctor Location not found."));

        boolean isDoctorOnLeave = doctorLeaveRepository
                .existsByDoctorLocationIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualAndLeaveStatus(
                        doctorLocationId, date, date, LeaveStatus.APPROVED
                );

        if (isDoctorOnLeave) {
            return;
        }

        DayOfWeek dayOfWeek = date.getDayOfWeek();

        List<DoctorSession> doctorSessions = sessionRepository
                .findByDoctorSchedule_DoctorLocation_IdAndDoctorSchedule_ServiceDay(doctorLocationId,dayOfWeek);

        for (DoctorSession doctorSession : doctorSessions) {
            generateSlotsForSession(doctorSession, date);
        }
    }

    private void generateSlotsForSession(DoctorSession doctorSession, LocalDate date) {
        LocalTime currentStartTime = doctorSession.getStartTime();

        short slotDuration = doctorSession
                .getDoctorSchedule()
                .getSlotDuration()
                .getDurationMins();

        while (
                currentStartTime
                        .plusMinutes(slotDuration)
                        .isBefore(doctorSession.getEndTime())
                        ||
                        currentStartTime
                                .plusMinutes(slotDuration)
                                .equals(doctorSession.getEndTime())
        ) {
            LocalTime currentEndTime = currentStartTime.plusMinutes(slotDuration);

            boolean isSlotAlreadyExists = slotRepository
                    .existsByDoctorSession_IdAndSlotDateAndStartTime(doctorSession.getId(), date, currentStartTime);

            if (!isSlotAlreadyExists) {
                Slot slot = Slot.builder()
                        .slotDate(date)
                        .doctorSession(doctorSession)
                        .startTime(currentStartTime)
                        .endTime(currentEndTime)
                        .status(SlotStatus.AVAILABLE)
                        .doctorLocation(doctorSession.getDoctorSchedule().getDoctorLocation())
                        .build();

                slotRepository.save(slot);
            }
            currentStartTime = currentEndTime;
        }
    }

    @Transactional
    public void generateSlotsForNextNDays(int days) {
        List<DoctorLocation> doctorLocations = doctorLocationRepository.findAll();
        LocalDate today = LocalDate.now();

        for (int i=0;i<days;i++) {
            LocalDate targetDate = today.plusDays(i);

            for (DoctorLocation location: doctorLocations) {
                generateSlotsForDateForLocation(location.getId(), targetDate);
            }
        }
    }
}
