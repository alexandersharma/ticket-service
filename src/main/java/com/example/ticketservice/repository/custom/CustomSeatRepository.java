package com.example.ticketservice.repository.custom;

import com.example.ticketservice.model.Seat;
import com.example.ticketservice.model.SeatHold;

import java.util.List;
import java.util.Optional;

public interface CustomSeatRepository {
    List<Seat> getAvailableSeats();
    
    List<Seat> getAvailableSeats(Integer venueLevel);

    boolean reserveSeats(int seatHoldId, String customerEmail);

    SeatHold holdSeats(int numSeats, Optional<Integer> minLevel, Optional<Integer> maxLevel, String customerEmail);

    void releaseExpiredHolds();
}
