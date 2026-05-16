package com.avinash.practoappointmentbookingsystem.entities.doctor;

import com.avinash.practoappointmentbookingsystem.enums.SpecialityPriority;
import com.avinash.practoappointmentbookingsystem.enums.SpecializationType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(
        name = "doctor_specializations",
        indexes = {
                @Index(name = "idx_doctor_specializations", columnList = "doctor_id, specialization, priority")
        }
)
@Data
public class DoctorSpecialization {

    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SpecializationType specialization;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SpecialityPriority priority = SpecialityPriority.SECONDARY;
}
