package com.example.theater.repositories;

import com.example.theater.entities.ScheduledPlay;
import com.example.theater.entities.TheaterPlay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduledPlayRepository extends JpaRepository<ScheduledPlay, Long> {
    List<ScheduledPlay> findByTheaterPlay(TheaterPlay theaterPlay);
}
