package com.avinash.practoappointmentbookingsystem.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
@Slf4j
public class SlotGenerationScheduler {

    private final Logger logger = Logger.getLogger("Schedule Logger");
    private final SlotGenerationService slotGenerationService;

    @Scheduled(cron = "0 0 0 * * *")
    public void generateUpcomingSlots() {
        log.info("Starting automatic Slots generation for each locations");

        slotGenerationService.generateSlotsForNextNDays(7);

        log.info("Slot generation finished successfully!");
    }
}
