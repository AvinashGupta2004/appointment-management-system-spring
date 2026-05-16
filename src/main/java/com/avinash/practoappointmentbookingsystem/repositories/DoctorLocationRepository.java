package com.avinash.practoappointmentbookingsystem.repositories;

import com.avinash.practoappointmentbookingsystem.entities.doctor.DoctorLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorLocationRepository extends JpaRepository<DoctorLocation, Long> {
}
