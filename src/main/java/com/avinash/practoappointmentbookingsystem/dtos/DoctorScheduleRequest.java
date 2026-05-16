package com.avinash.practoappointmentbookingsystem.dtos;

import com.avinash.practoappointmentbookingsystem.enums.SlotDuration;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class DoctorScheduleRequest {

    private Long doctorId;
    private Long doctorLocationId;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private SlotDuration slotDuration;

    @NotNull
    @FutureOrPresent
    private LocalDate validFrom;

    /*
        Optional.
        Null means indefinite schedule.
     */
    private LocalDate validTill;
}
