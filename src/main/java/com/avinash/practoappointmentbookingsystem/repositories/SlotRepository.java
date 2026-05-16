package com.avinash.practoappointmentbookingsystem.repositories;

import com.avinash.practoappointmentbookingsystem.entities.doctor.Slot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface SlotRepository extends JpaRepository<Slot, Long> {
    boolean existsByDoctorSession_IdAndSlotDateAndStartTime(Long id, LocalDate date, LocalTime currentStartTime);

    List<Slot> findByDoctorLocation_IdAndSlotDate(Long doctorLocationId, LocalDate date);
}
