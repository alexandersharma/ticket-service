package com.example.ticketservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.ticketservice.repository.custom.CustomSeatRepository;

@Service
public class ScheduledTasks {

   @Autowired
    private CustomSeatRepository customSeatRepository;

    // Every minute this task will check for expired holds
    @Scheduled(fixedRate = 60000)
    public void releaseExpiredHolds() {
        customSeatRepository.releaseExpiredHolds();
    }
}
