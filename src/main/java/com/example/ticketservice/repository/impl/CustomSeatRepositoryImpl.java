package com.example.ticketservice.repository.impl;

import com.example.ticketservice.enums.SeatStatus;
import com.example.ticketservice.model.Seat;
import com.example.ticketservice.model.SeatHold;
import com.example.ticketservice.repository.custom.CustomSeatRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomSeatRepositoryImpl implements CustomSeatRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Seat> getAvailableSeats() {
        TypedQuery<Seat> query = entityManager.createQuery(
                "SELECT s FROM Seat s WHERE s.status = 'AVAILABLE' ORDER BY s.id ASC",
                Seat.class
        );

        return query.getResultList();
    }

    @Override
    public List<Seat> getAvailableSeats(Integer venueLevel) {
        TypedQuery<Seat> query = entityManager.createQuery(
                "SELECT s FROM Seat s WHERE s.venueLevel = :venueLevel AND s.status = 'AVAILABLE' ORDER BY s.id ASC",
                Seat.class
        );

        query.setParameter("venueLevel", venueLevel);

        return query.getResultList();
    }

    @Override
    public SeatHold holdSeats(int numSeats, Optional<Integer> minLevel, Optional<Integer> maxLevel, String customerEmail) {
        // Construct the query
        StringBuilder queryBuilder = new StringBuilder("FROM Seat s WHERE s.status = 'AVAILABLE'");

        if (minLevel != null && minLevel.isPresent())
            queryBuilder.append(" AND s.venueLevel >= :minLevel");

        if (maxLevel != null && maxLevel.isPresent())
            queryBuilder.append(" AND s.venueLevel <= :maxLevel");

        TypedQuery<Seat> query = entityManager.createQuery(queryBuilder.toString(), Seat.class);

        if (minLevel != null && minLevel.isPresent())
            query.setParameter("minLevel", minLevel.get());

        if (maxLevel != null && maxLevel.isPresent())
            query.setParameter("maxLevel", maxLevel.get());

        // Set the limit
        query.setMaxResults(numSeats);

        // Get the seats
        List<Seat> seats = query.getResultList();

        if (seats.size() < numSeats) {
            // Not enough seats available
            return null;
        }

        // Update the seats to held status
        for (Seat seat : seats) {
            seat.setStatus(SeatStatus.HELD);
        }

        // Create a new SeatHold
        SeatHold seatHold = new SeatHold();
        seatHold.setSeats(seats);
        seatHold.setCustomerEmail(customerEmail);

        // Persist the seat hold
        entityManager.persist(seatHold);

        return seatHold;
    }

    @Override
    public boolean reserveSeats(int seatHoldId, String customerEmail) {
        // 1. Retrieve the SeatHold entry by its ID and the customer email
        SeatHold seatHold = entityManager.find(SeatHold.class, seatHoldId);

        if (seatHold == null) {
            return false; // No seat hold found for the given ID
        }

        if (!seatHold.getCustomerEmail().equals(customerEmail)) {
            return false; // SeatHold is not associated with the given email
        }

        // Optionally, you can check if the seat hold has expired.

        // 2. Reserve each held seat
        for (Seat seat : seatHold.getSeats()) {
            // Here, I'm assuming you have a status on the Seat entity to track its state.
            if (seat.getStatus() == SeatStatus.HELD) {
                seat.setStatus(SeatStatus.RESERVED);
            } else {
                // If any of the seats are not in the HELD status, fail the reservation.
                return false;
            }
        }

        return true;
    }

    @Override
    public void releaseExpiredHolds() {
        // 1. Get all expired SeatHold entries
        TypedQuery<SeatHold> query = entityManager.createQuery("FROM SeatHold sh WHERE sh.holdExpiryTime < :currentTime", SeatHold.class);
        query.setParameter("currentTime", new Date());
        List<SeatHold> expiredHolds = query.getResultList();

        // 2. For each expired SeatHold, release the seats
        for (SeatHold seatHold : expiredHolds) {
            for (Seat seat : seatHold.getSeats()) {
                seat.setStatus(SeatStatus.AVAILABLE);
            }

            // Optionally, you can remove the expired SeatHold or mark it as expired.
            entityManager.remove(seatHold);
        }
    }
}

