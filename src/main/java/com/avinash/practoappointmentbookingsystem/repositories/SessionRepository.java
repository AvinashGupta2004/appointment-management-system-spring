package com.avinash.practoappointmentbookingsystem.repositories;

import com.avinash.practoappointmentbookingsystem.entities.doctor.DoctorSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;

public interface SessionRepository extends JpaRepository<DoctorSession, Long> {
    List<DoctorSession> findByDoctorSchedule_DoctorLocation_IdAndDoctorSchedule_ServiceDay(Long doctorLocationId, DayOfWeek dayOfWeek);
}
