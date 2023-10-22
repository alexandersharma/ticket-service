package com.example.ticketservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.ticketservice.enums.SeatStatus;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "seats")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "venue_level")
    private Integer venueLevel;  // This denotes the level of the venue e.g., Orchestra, Main, Balcony 1, Balcony 2

    @Column(name = "row_number")
    private Integer rowNumber;   // The row number of the seat

    @Column(name = "seat_number")
    private Integer seatNumber;  // The specific seat number within that row

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SeatStatus status;   // The status of the seat, which can be: AVAILABLE, HELD, RESERVED

    @Column(name = "customer_email")
    private String customerEmail;  // The email of the customer who reserved or held the seat. NULL if the seat is available.

    @ManyToOne
    @JoinColumn(name = "seat_hold_id") // This is the foreign key column in the 'seats' table.
    private SeatHold seatHoldId;
}
