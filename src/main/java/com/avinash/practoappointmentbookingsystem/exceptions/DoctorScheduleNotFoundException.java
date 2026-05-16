package com.avinash.practoappointmentbookingsystem.exceptions;

public class DoctorScheduleNotFoundException extends RuntimeException {
    public DoctorScheduleNotFoundException(String message) {
        super(message);
    }
}
