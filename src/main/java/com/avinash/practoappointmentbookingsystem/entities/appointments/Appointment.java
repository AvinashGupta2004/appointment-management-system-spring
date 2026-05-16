package com.avinash.practoappointmentbookingsystem.entities.appointments;

import com.avinash.practoappointmentbookingsystem.entities.patient.Patient;
import com.avinash.practoappointmentbookingsystem.entities.User;
import com.avinash.practoappointmentbookingsystem.entities.doctor.Doctor;
import com.avinash.practoappointmentbookingsystem.entities.doctor.DoctorLocation;
import com.avinash.practoappointmentbookingsystem.entities.doctor.Slot;
import com.avinash.practoappointmentbookingsystem.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(
        name = "appointments",

        indexes = {

                @Index(
                        name = "idx_appointment_doctor_date",
                        columnList = "doctor_id, appointment_date"
                ),

                @Index(
                        name = "idx_appointment_patient",
                        columnList = "patient_id"
                ),

                @Index(
                        name = "idx_appointment_slot",
                        columnList = "slot_id"
                ),

                @Index(
                        name = "idx_appointment_status",
                        columnList = "status"
                ),

                @Index(
                        name = "idx_appointment_location",
                        columnList = "doctor_location_id"
                )
        },

        uniqueConstraints = {

                @UniqueConstraint(
                        name = "uk_appointment_slot",
                        columnNames = "slot_id"
                )
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
        User who booked appointment.

        Example:
        Son booking for father.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "booked_by_user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_appointment_booked_user")
    )
    private User bookedByUser;

    /*
        Actual consultation receiver.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "patient_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_appointment_patient")
    )
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "doctor_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_appointment_doctor")
    )
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "doctor_location_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_appointment_location")
    )
    private DoctorLocation doctorLocation;

    /*
        One slot = one appointment.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "slot_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_appointment_slot")
    )
    private Slot slot;

    @Column(name = "appointment_date", nullable = false)
    private LocalDate appointmentDate;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    /*
        BOOKED
        COMPLETED
        CANCELLED
        NO_SHOW
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private AppointmentStatus status;

    /*
        Snapshot fields.
        Historical consistency.
     */
    @Column(name = "patient_name_snapshot", nullable = false)
    private String patientNameSnapshot;

    @Column(name = "patient_mobile_snapshot")
    private String patientMobileSnapshot;

    @Column(name = "patient_email_snapshot")
    private String patientEmailSnapshot;

    @Column(name = "doctor_name_snapshot", nullable = false)
    private String doctorNameSnapshot;

    /*
        Optional consultation reason.
     */
    @Column(name = "consultation_reason", columnDefinition = "TEXT")
    private String consultationReason;

    /*
        Optional quick note before consultation.
     */
    @Column(name = "patient_note", columnDefinition = "TEXT")
    private String patientNote;

    /*
        Optional cancellation reason.
     */
    @Column(name = "cancellation_reason")
    private String cancellationReason;

    @Column(name = "booked_at", nullable = false, updatable = false)
    private LocalDateTime bookedAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {

        validateAppointmentTiming();

        if(status == null) {
            status = AppointmentStatus.BOOKED;
        }

        this.bookedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {

        validateAppointmentTiming();

        this.updatedAt = LocalDateTime.now();
    }

    private void validateAppointmentTiming() {

        if(startTime != null && endTime != null) {

            if(!endTime.isAfter(startTime)) {

                throw new IllegalArgumentException(
                        "Appointment end time must be after start time"
                );
            }
        }
    }
}
