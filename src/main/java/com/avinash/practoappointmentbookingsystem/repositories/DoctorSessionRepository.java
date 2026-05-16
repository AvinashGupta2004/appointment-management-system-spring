package com.avinash.practoappointmentbookingsystem.repositories;

import com.avinash.practoappointmentbookingsystem.entities.doctor.DoctorSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;

@Repository
public interface DoctorSessionRepository extends JpaRepository<DoctorSession, Long> {
    boolean existsByDoctorSchedule_IdAndActiveAndStartTimeLessThanAndEndTimeGreaterThan(Long doctorScheduleId, LocalTime t1, LocalTime t2);
}
