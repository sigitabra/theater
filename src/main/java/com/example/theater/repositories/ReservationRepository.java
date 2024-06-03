package com.example.theater.repositories;

import com.example.theater.entities.Reservation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Transactional
    void deleteReservationsByScheduledPlayId(Long scheduledPlayId);


}
