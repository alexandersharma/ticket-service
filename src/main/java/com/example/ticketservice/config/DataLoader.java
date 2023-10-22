package com.example.ticketservice.config;

import com.example.ticketservice.enums.SeatStatus;
import com.example.ticketservice.model.Level;
import com.example.ticketservice.model.Seat;
import com.example.ticketservice.repository.LevelRepository;
import com.example.ticketservice.repository.SeatRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {
    @Autowired
    private LevelRepository levelRepository;
    @Autowired
    private SeatRepository seatRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (levelRepository.count() == 0) {
            Level orchestra = new Level(1, "Orchestra", 100.00, 25, 50);
            Level main = new Level(2, "Main", 75.00, 20, 100);
            Level balcony1 = new Level(3, "Balcony 1", 50.00, 15, 100);
            Level balcony2 = new Level(4, "Balcony 2", 40.00, 15, 100);

            levelRepository.save(orchestra);
            levelRepository.save(main);
            levelRepository.save(balcony1);
            levelRepository.save(balcony2);

            initializeSeats(orchestra);
            initializeSeats(main);
            initializeSeats(balcony1);
            initializeSeats(balcony2);
        }
    }

    private void initializeSeats(Level level) {
        for (int i = 1; i <= level.getRows(); i++) {
            for (int j = 1; j <= level.getSeatsInRow(); j++) {
                Seat seat = new Seat();
                seat.setVenueLevel(level.getId());
                seat.setRowNumber(i);
                seat.setSeatNumber(j);
                seat.setStatus(SeatStatus.AVAILABLE);
                seatRepository.save(seat);
            }
        }
    }
}
