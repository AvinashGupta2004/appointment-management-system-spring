package com.avinash.practoappointmentbookingsystem.repositories;

import com.avinash.practoappointmentbookingsystem.entities.doctor.DoctorLeave;
import com.avinash.practoappointmentbookingsystem.enums.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface DoctorLeaveRepository extends JpaRepository<DoctorLeave, Long> {


    boolean existsByDoctorLocationIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualAndLeaveStatus(Long doctorLocationId, LocalDate startDateIsLessThan, LocalDate endDateIsGreaterThan, LeaveStatus leaveStatus);
}
