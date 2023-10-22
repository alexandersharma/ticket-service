package com.example.ticketservice.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HoldRequest {
    private int numSeats;
    private Optional<Integer> minLevel;
    private Optional<Integer> maxLevel;
    private String customerEmail;
}
