package com.example.ticketservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "levels")
public class Level {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "level_name", nullable = false)
    private String levelName;  // e.g., Orchestra, Main, Balcony 1, Balcony 2

    @Column(name = "price", nullable = false)
    private Double price;  // Price for the seat in this level

    @Column(name = "rows", nullable = false)
    private Integer rows;  // Number of rows in this level

    @Column(name = "seats_in_row", nullable = false)
    private Integer seatsInRow;  // Number of seats in each row of this level

}
