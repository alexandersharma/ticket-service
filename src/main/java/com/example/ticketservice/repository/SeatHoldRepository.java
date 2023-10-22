package com.example.ticketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ticketservice.model.SeatHold;

@Repository
public interface SeatHoldRepository extends JpaRepository<SeatHold, Integer> {
}
