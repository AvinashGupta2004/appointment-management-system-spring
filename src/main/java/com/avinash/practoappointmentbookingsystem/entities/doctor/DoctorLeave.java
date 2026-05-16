package com.avinash.practoappointmentbookingsystem.entities.doctor;

import com.avinash.practoappointmentbookingsystem.enums.LeaveStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "doctor_leaves"
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorLeave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
        Leave belongs to specific location.

        Example:
        Apollo closed
        Online consultation still active
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "doctor_location_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_leave_location")
    )
    private DoctorLocation doctorLocation;

    /*
        Leave start date.
     */
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    /*
        Leave end date.
     */
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    /*
        Optional reason.
     */
    @Column(length = 255)
    private String reason;

    @Enumerated(EnumType.STRING)
    private LeaveStatus leaveStatus;

    /*
        Soft disable support.
     */
    @Column(nullable = false)
    private Boolean active = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {

        validateLeaveDates();

        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {

        validateLeaveDates();

        this.updatedAt = LocalDateTime.now();
    }

    private void validateLeaveDates() {

        if(startDate != null && endDate != null) {

            if(endDate.isBefore(startDate)) {

                throw new IllegalArgumentException(
                        "Leave end date cannot be before start date"
                );
            }
        }
    }
}
