package com.avinash.practoappointmentbookingsystem.entities.doctor;

import com.avinash.practoappointmentbookingsystem.enums.SlotStatus;
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
        name = "slots"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Slot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
        Slot belongs to a location.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "doctor_location_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_slot_location")
    )
    private DoctorLocation doctorLocation;

    /*
        Session from which slot generated.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "session_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_slot_session")
    )
    private DoctorSession doctorSession;

    /*
        Actual appointment date.
     */
    @Column(name = "slot_date", nullable = false)
    private LocalDate slotDate;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    /*
        AVAILABLE
        BOOKED
        BLOCKED
        CANCELLED
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private SlotStatus status;


    /*
        Optional operational flags.
     */
    @Column(name = "manually_blocked")
    private Boolean manuallyBlocked = false;

    /*
        Useful for:
        admin block reason
        emergency closure
        maintenance
     */
    @Column(name = "block_reason")
    private String blockReason;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {

        validateSlotTiming();

        if(status == null) {
            status = SlotStatus.AVAILABLE;
        }

        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {

        validateSlotTiming();

        this.updatedAt = LocalDateTime.now();
    }

    private void validateSlotTiming() {

        if(startTime != null && endTime != null) {

            if(!endTime.isAfter(startTime)) {

                throw new IllegalArgumentException(
                        "Slot end time must be after start time"
                );
            }
        }
    }
}
