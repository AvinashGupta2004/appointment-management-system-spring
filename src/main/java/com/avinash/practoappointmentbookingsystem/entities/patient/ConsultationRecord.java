package com.avinash.practoappointmentbookingsystem.entities.patient;

import com.avinash.practoappointmentbookingsystem.entities.appointments.Appointment;
import com.avinash.practoappointmentbookingsystem.entities.doctor.Doctor;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "consultation_records",
        indexes = {

                @Index(
                        name = "idx_consultation_appointment",
                        columnList = "appointment_id"
                ),

                @Index(
                        name = "idx_consultation_patient",
                        columnList = "patient_id"
                ),

                @Index(
                        name = "idx_consultation_doctor",
                        columnList = "doctor_id"
                ),

                @Index(
                        name = "idx_consultation_date",
                        columnList = "consultation_date"
                )
        },

        uniqueConstraints = {

                @UniqueConstraint(
                        name = "uk_consultation_appointment",
                        columnNames = "appointment_id"
                )
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsultationRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
        One appointment -> one consultation record.
     */
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "appointment_id",
            nullable = false,
            unique = true,
            foreignKey = @ForeignKey(name = "fk_consultation_appointment")
    )
    private Appointment appointment;

    /*
        Denormalized for faster querying.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "patient_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_consultation_patient")
    )
    private Patient patient;

    /*
        Denormalized for dashboard/history queries.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "doctor_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_consultation_doctor")
    )
    private Doctor doctor;

    @Column(name = "consultation_date", nullable = false)
    private LocalDate consultationDate;

    /*
        Patient symptoms.
     */
    @Column(columnDefinition = "TEXT")
    private String symptoms;

    /*
        Clinical diagnosis.
     */
    @Column(columnDefinition = "TEXT")
    private String diagnosis;

    /*
        Doctor observations/notes.
     */
    @Column(name = "doctor_notes", columnDefinition = "TEXT")
    private String doctorNotes;

    /*
        Recommended tests.
     */
    @Column(name = "recommended_tests", columnDefinition = "TEXT")
    private String recommendedTests;

    /*
        Follow-up instructions.
     */
    @Column(name = "follow_up_instructions", columnDefinition = "TEXT")
    private String followUpInstructions;

    /*
        Optional next follow-up date.
     */
    @Column(name = "follow_up_date")
    private LocalDate followUpDate;

    /*
        Indicates consultation completed.
     */
    @Column(nullable = false)
    private Boolean completed = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {

        if (consultationDate == null) {
            consultationDate = LocalDate.now();
        }

        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}