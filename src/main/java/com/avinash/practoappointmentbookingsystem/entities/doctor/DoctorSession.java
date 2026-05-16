package com.avinash.practoappointmentbookingsystem.entities.doctor;

import com.avinash.practoappointmentbookingsystem.enums.SessionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(
        name = "sessions"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
        Session belongs to a specific schedule/day.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "doctor_schedule_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_session_schedule")
    )
    private DoctorSchedule doctorSchedule;

    /*
        Example:
        Morning OPD
        Evening Consultation
     */
    @Column(name = "session_name", length = 100)
    @Enumerated(EnumType.STRING)
    private SessionType sessionName;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    /*
        Maximum appointments allowed
        in this session.

        Optional operational control.
     */
    @Column(name = "max_appointments")
    private Integer maxAppointments;

    /*
        Supports temporary disabling
        without deletion.
     */
    @Column(nullable = false)
    private Boolean active = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {

        if(startTime != null && endTime != null) {

            if(!endTime.isAfter(startTime)) {
                throw new IllegalArgumentException(
                        "End time must be after start time"
                );
            }
        }

        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {

        if(startTime != null && endTime != null) {

            if(!endTime.isAfter(startTime)) {
                throw new IllegalArgumentException(
                        "End time must be after start time"
                );
            }
        }

        this.updatedAt = LocalDateTime.now();
    }
}
