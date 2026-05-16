package com.avinash.practoappointmentbookingsystem.repositories;

import com.avinash.practoappointmentbookingsystem.entities.appointments.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    long countBySlot_Id(Long slotId);
}
