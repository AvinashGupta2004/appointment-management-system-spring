package com.avinash.practoappointmentbookingsystem.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SlotDuration {
    SHORT((short) 30, (short) 4),
    LONG((short) 60, (short) 8);

    private final Short durationMins;
    private final Short maxCapacityOfPatients;
}
