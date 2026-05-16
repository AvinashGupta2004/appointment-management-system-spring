package com.avinash.practoappointmentbookingsystem.services;

import com.avinash.practoappointmentbookingsystem.dtos.DoctorRegistrationRequest;
import com.avinash.practoappointmentbookingsystem.entities.User;
import com.avinash.practoappointmentbookingsystem.entities.doctor.Doctor;
import com.avinash.practoappointmentbookingsystem.enums.UserRole;
import com.avinash.practoappointmentbookingsystem.exceptions.DoctorLocationNotFoundException;
import com.avinash.practoappointmentbookingsystem.exceptions.DoctorNotFoundException;
import com.avinash.practoappointmentbookingsystem.exceptions.UserNotFoundException;
import com.avinash.practoappointmentbookingsystem.repositories.DoctorRepository;
import com.avinash.practoappointmentbookingsystem.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Long createDoctor(DoctorRegistrationRequest request) {
        if (userRepository.existsByEmailAddress(request.getEmail())) {
            throw new UserNotFoundException("User already Exists!");
        }

        User user = User.builder()
                .emailAddress(request.getEmail())
                .name(request.getFullName())
                .password(passwordEncoder.encode(request.getPassword()))
                .contactNumber(request.getMobile())
                .userRole(UserRole.ROLE_DOCTOR)
                .build();

        Doctor doctor = Doctor.builder()
                .user(user)
                .doctorRegistrationId(request.getDoctorRegistrationId())
                .experienceYears(request.getExperienceYears())
                .qualification(request.getQualification())
                .consultationFees(request.getConsultationFees())
                .verified(false)
                .build();

        doctorRepository.save(doctor);
        return doctor.getId();
    }

    public Doctor getDoctor(Long doctorId) {
        return doctorRepository
                .findById(doctorId)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not Found!!"));
    }

    public Doctor deleteDoctor(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new DoctorLocationNotFoundException("Doctor not found!!"));
        doctorRepository.deleteById(doctorId);
        return doctor;
    }
}
