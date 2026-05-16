package com.avinash.practoappointmentbookingsystem.entities.patient;

import com.avinash.practoappointmentbookingsystem.entities.doctor.Doctor;
import com.avinash.practoappointmentbookingsystem.enums.MedicalRecordType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "medical_records",

        indexes = {

                @Index(
                        name = "idx_medical_record_patient",
                        columnList = "patient_id"
                ),

                @Index(
                        name = "idx_medical_record_doctor",
                        columnList = "doctor_id"
                ),

                @Index(
                        name = "idx_medical_record_type",
                        columnList = "record_type"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
        Patient owner.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "patient_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_medical_record_patient")
    )
    private Patient patient;

    /*
        Doctor who uploaded/generated record.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "doctor_id",
            foreignKey = @ForeignKey(name = "fk_medical_record_doctor")
    )
    private Doctor doctor;

    /*
        Generic categorization.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "record_type", nullable = false, length = 50)
    private MedicalRecordType recordType;

    /*
        Example:
        MRI Brain Report
        Previous Surgery
        Allergy Note
     */
    @Column(nullable = false)
    private String title;

    /*
        Optional detailed description.
     */
    @Column(columnDefinition = "TEXT")
    private String description;

    /*
        File stored externally:
        S3 / Cloudinary / filesystem
     */
    @Column(name = "file_url")
    private String fileUrl;

    /*
        Original uploaded filename.
     */
    @Column(name = "file_name")
    private String fileName;

    /*
        MIME type:
        application/pdf
        image/png
     */
    @Column(name = "content_type")
    private String contentType;

    /*
        Soft deletion support.
     */
    @Column(nullable = false)
    private Boolean active = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {

        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {

        this.updatedAt = LocalDateTime.now();
    }
}
