package com.avinash.practoappointmentbookingsystem.entities.patient;

import com.avinash.practoappointmentbookingsystem.entities.doctor.Doctor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "prescriptions",

        indexes = {

                @Index(
                        name = "idx_prescription_consultation",
                        columnList = "consultation_record_id"
                ),

                @Index(
                        name = "idx_prescription_patient",
                        columnList = "patient_id"
                ),

                @Index(
                        name = "idx_prescription_doctor",
                        columnList = "doctor_id"
                )
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
        Prescription belongs to consultation.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "consultation_record_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_prescription_consultation")
    )
    private ConsultationRecord consultationRecord;

    /*
        Denormalized for faster querying.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "patient_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_prescription_patient")
    )
    private Patient patient;

    /*
        Prescribing doctor.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "doctor_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_prescription_doctor")
    )
    private Doctor doctor;

    /*
        Medicine name.
     */
    @Column(name = "medicine_name", nullable = false)
    private String medicineName;

    /*
        Example:
        500mg
        1 tablet
     */
    @Column(nullable = false)
    private String dosage;

    /*
        Example:
        Twice daily
        Morning after food
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String frequency;

    /*
        Example:
        5 days
        2 weeks
     */
    @Column(nullable = false)
    private String duration;

    /*
        Optional medicine instructions.
     */
    @Column(columnDefinition = "TEXT")
    private String instructions;

    /*
        Soft delete support.
     */
    @Column(nullable = false)
    private Boolean active = true;

    @Column(name = "prescribed_at", nullable = false, updatable = false)
    private LocalDateTime prescribedAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {

        this.prescribedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {

        this.updatedAt = LocalDateTime.now();
    }
}
