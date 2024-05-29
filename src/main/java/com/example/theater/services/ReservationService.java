package com.example.theater.services;

import com.example.theater.entities.Reservation;
import com.example.theater.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public Reservation getReservationById(Long id) {
        log.info("START | getReservationById | Getting Reservation by id {}", id);
        Reservation reservation = reservationRepository.findById(id).orElse(null);
        if (reservation == null) {
            log.info("END | getReservationById | No Reservation found with id {}", id);
        } else {
            log.info("END | getReservationById | Returning Reservation {}", reservation);
        }
        return reservation;
    }


    public void addReservation(Reservation reservation) {
        log.info("START | addReservation");
        reservationRepository.saveAndFlush(reservation);
    }


    public void deleteReservationById(Long reservationId) {
        log.info("deleteReservation {}", reservationId);

        reservationRepository.deleteById(reservationId);
    }


    public void deleteReservationsByScheduledPlayId(Long scheduledPlayId) {
        log.info("deleteReservationByScheduledPlayId {}", scheduledPlayId);
        reservationRepository.deleteReservationsByScheduledPlayId(scheduledPlayId);
    }


}
