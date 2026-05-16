package com.avinash.practoappointmentbookingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PractoAppointmentBookingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(PractoAppointmentBookingSystemApplication.class, args);
    }

}
