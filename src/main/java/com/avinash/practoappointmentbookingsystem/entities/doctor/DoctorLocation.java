package com.avinash.practoappointmentbookingsystem.entities.doctor;

import com.avinash.practoappointmentbookingsystem.enums.ConsultationMode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
@Entity
@Table(
        name = "doctor_locations",
        indexes = {
                @Index(name = "idx_location_doctor", columnList = "doctor_id"),
                @Index(name = "idx_location_city", columnList = "city"),
                @Index(name = "idx_location_active", columnList = "active")
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "doctor_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_location_doctor")
    )
    private Doctor doctor;

    @Column(name = "location_name", nullable = false, length = 150)
    private String locationName;

    @Column(nullable = false, length = 255)
    private String address;

    @Column(length = 100)
    private String city;

    @Column(length = 100)
    private String state;

    @Column(length = 100)
    private String country;

    @Column(name = "postal_code", length = 20)
    private String postalCode;

    private Double latitude;

    private Double longitude;

    @Column(name = "consultation_fee")
    private Integer consultationFee;

    @Enumerated(EnumType.STRING)
    @Column(name = "consultation_mode", nullable = false, length = 30)
    private ConsultationMode consultationMode;

    @Column(name = "primary_location")
    private boolean primaryLocation = false;

    @Column(nullable = false)
    private boolean active = true;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
