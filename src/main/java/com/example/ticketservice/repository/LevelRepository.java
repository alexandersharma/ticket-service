package com.example.ticketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ticketservice.model.Level;

@Repository
public interface LevelRepository extends JpaRepository<Level, Integer> {
    
}

