package com.example.theater.repositories;

import com.example.theater.entities.TheaterPlay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheaterPlayRepository extends JpaRepository<TheaterPlay, Long> {
}
