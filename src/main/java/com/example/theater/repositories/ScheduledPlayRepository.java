package com.example.theater.repositories;

import com.example.theater.entities.ScheduledPlay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduledPlayRepository extends JpaRepository<ScheduledPlay, Long> {
}
