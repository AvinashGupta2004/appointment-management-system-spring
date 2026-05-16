package com.avinash.practoappointmentbookingsystem.repositories;

import com.avinash.practoappointmentbookingsystem.entities.doctor.DoctorSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, Long> {
}
