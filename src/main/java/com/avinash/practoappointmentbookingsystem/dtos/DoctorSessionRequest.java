package com.avinash.practoappointmentbookingsystem.dtos;

import com.avinash.practoappointmentbookingsystem.enums.SessionType;
import lombok.Data;

import java.time.LocalTime;

@Data
public class DoctorSessionRequest {

    private Long doctorScheduleId;
    private SessionType sessionType;
    private LocalTime startTime;
    private LocalTime endTime;

}
