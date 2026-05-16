package com.avinash.practoappointmentbookingsystem.services;

import com.avinash.practoappointmentbookingsystem.dtos.AppointmentRequest;
import com.avinash.practoappointmentbookingsystem.entities.User;
import com.avinash.practoappointmentbookingsystem.entities.appointments.Appointment;
import com.avinash.practoappointmentbookingsystem.entities.doctor.Slot;
import com.avinash.practoappointmentbookingsystem.entities.patient.Patient;
import com.avinash.practoappointmentbookingsystem.enums.AppointmentStatus;
import com.avinash.practoappointmentbookingsystem.enums.SlotStatus;
import com.avinash.practoappointmentbookingsystem.exceptions.SlotNotAvailableException;
import com.avinash.practoappointmentbookingsystem.exceptions.SlotNotFoundException;
import com.avinash.practoappointmentbookingsystem.exceptions.UserNotFoundException;
import com.avinash.practoappointmentbookingsystem.repositories.AppointmentRepository;
import com.avinash.practoappointmentbookingsystem.repositories.PatientRepository;
import com.avinash.practoappointmentbookingsystem.repositories.SlotRepository;
import com.avinash.practoappointmentbookingsystem.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppointmentBookingService {

    private final SlotRepository slotRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final RedisLockService redisLockService;
    private final UserRepository userRepository;

    @Transactional
    public Long bookAppointment(AppointmentRequest request) {
        String lockKey = "slot:booking:" + request.getSlotId();
        String lockValue = UUID.randomUUID().toString();
        boolean lockAcquired = redisLockService.acquireLock(lockKey, lockValue, Duration.ofSeconds(30));

        if (!lockAcquired) {
            throw new SlotNotAvailableException("Slot is currently busy. Please try again!");
        }

        try {
            Slot slot = slotRepository.findById(request.getSlotId()).orElseThrow(() -> new SlotNotFoundException("Slot not found!"));

            long currentBookings = appointmentRepository.countBySlot_Id(slot.getId());
            Short maxCapacity = slot.getDoctorSession()
                                    .getDoctorSchedule()
                                    .getSlotDuration()
                                    .getMaxCapacityOfPatients();

            if (currentBookings >= maxCapacity) {
                slot.setStatus(SlotStatus.FULL);
                slotRepository.save(slot);
                throw new SlotNotAvailableException("No more bookings allowed for this slot!");
            }
            User user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new UserNotFoundException("No user found with the given User ID! Hence booking not Allowed!"));

            Patient patient = Patient.builder()
                    .name(request.getPatientName())
                    .dateOfBirth(request.getDateOfBirth())
                    .gender(request.getGender())
                    .contactNumber(request.getMobileNumber())
                    .emailAddress(request.getEmail())
                    .user(user)
                    .build();

            patientRepository.save(patient);

            Appointment appointment = Appointment.builder()
                    .doctor(slot.getDoctorLocation().getDoctor())
                    .patient(patient)
                    .doctorLocation(slot.getDoctorLocation())
                    .slot(slot)
                    .appointmentDate(slot.getSlotDate())
                    .startTime(slot.getStartTime())
                    .endTime(slot.getEndTime())
                    .status(AppointmentStatus.BOOKED)
                    .build();

            appointmentRepository.save(appointment);

            if (currentBookings + 1 == maxCapacity) {
                slot.setStatus(SlotStatus.FULL);
            }
            else {
                slot.setStatus(SlotStatus.AVAILABLE);
            }
            slotRepository.save(slot);
            return appointment.getId();
        }
        finally {
            redisLockService.releaseLock(lockKey);
        }
    }
}
