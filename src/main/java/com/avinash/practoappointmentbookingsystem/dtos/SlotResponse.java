package com.avinash.practoappointmentbookingsystem.dtos;

import com.avinash.practoappointmentbookingsystem.enums.SlotStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;

@Data
@Builder
public class SlotResponse {

    private Long slotId;
    private LocalTime startTime;
    private LocalTime endTime;
    private SlotStatus slotStatus;
}
