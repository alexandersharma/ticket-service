package com.example.ticketservice.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ticketservice.dto.HoldRequest;
import com.example.ticketservice.dto.ReserveRequest;
import com.example.ticketservice.model.SeatHold;
import com.example.ticketservice.service.TicketService;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping("/available")
    public int getAvailableSeats(@RequestParam Optional<Integer> venueLevel) {
        return ticketService.numSeatsAvailable(venueLevel);
    }

    @PostMapping("/hold")
    public SeatHold holdSeats(@RequestBody HoldRequest request) {
        return ticketService.findAndHoldSeats(request.getNumSeats(), 
            request.getMinLevel(), request.getMaxLevel(), request.getCustomerEmail());
    }

    @PostMapping("/reserve")
    public String reserveSeats(@RequestBody ReserveRequest request) {
        return ticketService.reserveSeats(request.getSeatHoldId(), request.getCustomerEmail());
    }
}

