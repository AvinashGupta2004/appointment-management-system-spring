package com.avinash.practoappointmentbookingsystem.enums;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public enum SpecializationType {
    // Top & Common Specialties
    DENTIST("Dentist"),
    GYNECOLOGIST("Gynecologist/Obstetrician"),
    DIETITIAN_NUTRITIONIST("Dietitian/Nutritionist"),
    PHYSIOTHERAPIST("Physiotherapist"),
    GENERAL_SURGEON("General Surgeon"),
    ORTHOPEDIST("Orthopedist"),
    GENERAL_PHYSICIAN("General Physician"),
    PEDIATRICIAN("Pediatrician"),
    GASTROENTEROLOGIST("Gastroenterologist"),
    DERMATOLOGIST("Dermatologist"),

    // Specialized Medicine
    CARDIOLOGIST("Cardiologist"),
    NEUROLOGIST("Neurologist"),
    NEUROSURGEON("Neurosurgeon"),
    NEPHROLOGIST("Nephrologist"),
    ENDOCRINOLOGIST("Endocrinologist"),
    PULMONOLOGIST("Pulmonologist"),
    RHEUMATOLOGIST("Rheumatologist"),
    UROLOGIST("Urologist"),
    ONCOLOGIST("Oncologist"),
    HEMATOLOGIST("Hematologist"),
    INFECTIOUS_DISEASE_SPECIALIST("Infectious Disease Specialist"),

    // Specialized Surgery & Care
    OPHTHALMOLOGIST("Ophthalmologist"),
    ENT_SPECIALIST("ENT Specialist"),
    PSYCHIATRIST("Psychiatrist"),
    CARDIOTHORACIC_SURGEON("Cardiothoracic Surgeon"),
    PLASTIC_SURGEON("Plastic Surgeon"),
    URO_SURGEON("Urosurgeon"),
    PAEDIATRIC_SURGEON("Paediatric Surgeon"),
    LAPAROSCOPIC_SURGEON("Laparoscopic Surgeon"),
    NEONATOLOGIST("Neonatologist"),

    // Other Specialist
    RADIOLOGIST("Radiologist"),
    PATHOLOGIST("Pathologist"),
    DIABETOLOGIST("Diabetologist"),
    SEXOLOGIST("Sexologist"),
    HOMEOPATH("Homeopath"),
    AYURVEDA("Ayurveda");

    private final String displayName;
}
