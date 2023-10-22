package com.example.ticketservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ticketservice.repository.SeatHoldRepository;
import com.example.ticketservice.repository.SeatRepository;
import com.example.ticketservice.repository.custom.CustomSeatRepository;
import com.example.ticketservice.service.TicketService;

import com.example.ticketservice.model.SeatHold;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private SeatHoldRepository seatHoldRepository;

    @Autowired
    private CustomSeatRepository customSeatRepository;

    @Override
    @Transactional
    public SeatHold findAndHoldSeats(int numSeats, Optional<Integer> minLevel, Optional<Integer> maxLevel, String customerEmail) {
        SeatHold seatHold = customSeatRepository.holdSeats(numSeats, minLevel, maxLevel, customerEmail);
    
        if (seatHold != null) {
            // Assuming hold expires in 60 seconds; set the expiry time in the seatHold entity
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.SECOND, 60);
            seatHold.setHoldExpiryTime(calendar.getTime());

            // Save or update the seat hold entity with the new expiry time
            seatHoldRepository.save(seatHold);
        }


        return seatHold;
    }


    @Override
    @Transactional
    public String reserveSeats(int seatHoldId, String customerEmail) {
        boolean isReserved = customSeatRepository.reserveSeats(seatHoldId, customerEmail);

        if (isReserved) {
            // Return a unique reservation confirmation code (for simplicity, using UUID)
            return UUID.randomUUID().toString();
        } else {
            // You can handle this better based on your needs - maybe throw a custom exception or return a null/error message
            return "Reservation failed!";
        }
    }

    @Override
    public int numSeatsAvailable(Optional<Integer> venueLevel) {
        // Check if venueLevel is present. If not, fetch available seats for all levels.
        if (venueLevel.isPresent()) {
            return customSeatRepository.getAvailableSeats(venueLevel.get()).size();
        } else {
            // Fetch all available seats across all levels. 
            // This assumes you have an overload of the getAvailableSeats method without parameters or another method to fetch all available seats.
            return customSeatRepository.getAvailableSeats().size();
        }
    }
}

