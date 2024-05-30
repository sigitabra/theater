package com.example.theater.services;

import com.example.theater.dto.ReservationIn;
import com.example.theater.dto.ScheduledPlayIn;
import com.example.theater.entities.Reservation;
import com.example.theater.entities.ScheduledPlay;
import com.example.theater.repositories.ScheduledPlayRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ScheduledPlayService {

    private final ScheduledPlayRepository scheduledPlayRepository;

    private final TheaterPlayService theaterPlayService;

    private final ReservationService reservationService;


    public List<ScheduledPlay> getAllScheduledPlay() {
        log.info("START | getAllScheduledPlay");
        List<ScheduledPlay> scheduledPlays = scheduledPlayRepository.findAll();
        log.info("END | getAllScheduledPlay");
        return scheduledPlays;
    }


    public ScheduledPlay getScheduledPlayById(Long id) {
        log.info("START | getScheduledPlayById | id={}", id);
        ScheduledPlay scheduledPlay = scheduledPlayRepository.findById(id).orElse(null);
        if (scheduledPlay == null) {
            log.info("END | getScheduledPlayById | No scheduled play found with id {}", id);
        } else {
            log.info("END | getScheduledPlayById | Found scheduled play with id {}", id);
        }
        return scheduledPlay;
    }


    public ScheduledPlay addScheduledPlay(ScheduledPlayIn scheduledPlayIn) {
        log.info("START | addScheduledPlay");
        ScheduledPlay scheduledPlay = new ScheduledPlay();
        scheduledPlay.setTheaterPlay(theaterPlayService.getPlayById(scheduledPlayIn.getTheaterPlayId()));
        scheduledPlay.setAddress(scheduledPlayIn.getAddress());
        scheduledPlay.setDate(scheduledPlayIn.getDate());
        scheduledPlay.setTime(scheduledPlayIn.getTime());
        scheduledPlay.setTotalSeats(scheduledPlayIn.getTotalSeats());
        scheduledPlay.setTotalReservedSeats(0);
        scheduledPlay.setReservations(new ArrayList<>());
        log.info("END | addScheduledPlay | ScheduledPlay added: {}", scheduledPlay);
        return scheduledPlayRepository.saveAndFlush(scheduledPlay);
    }


    public ScheduledPlay updateScheduledPlay(Long scheduledPlayId, ScheduledPlayIn scheduledPlayIn) {
        log.info("START | updateScheduledPlay");
        ScheduledPlay scheduledPlay = getScheduledPlayById(scheduledPlayId);
        if (scheduledPlay == null) {
            return null;
        }
        if (scheduledPlayIn.getAddress() != null && !scheduledPlayIn.getAddress().equals(scheduledPlay.getAddress())) {
            scheduledPlay.setAddress(scheduledPlayIn.getAddress());
        }
        if (scheduledPlayIn.getDate() != null && !scheduledPlayIn.getDate().equals(scheduledPlay.getDate())) {
            scheduledPlay.setDate(scheduledPlayIn.getDate());
        }
        if (scheduledPlayIn.getTime() != null && !scheduledPlayIn.getTime().equals(scheduledPlay.getTime())) {
            scheduledPlay.setTime(scheduledPlayIn.getTime());
        }
        if (scheduledPlayIn.getTotalSeats() != 0 && scheduledPlayIn.getTotalSeats() != scheduledPlay.getTotalSeats()) {
            scheduledPlay.setTotalSeats(scheduledPlayIn.getTotalSeats());
        }

        log.info("END | updateScheduledPlay | ScheduledPlay updated: {}", scheduledPlay);
        return scheduledPlayRepository.saveAndFlush(scheduledPlay);
    }


    public void deleteScheduledPlayById(Long scheduledPlayId) {
        log.info("START | deleteScheduledPlay");
        reservationService.deleteReservationsByScheduledPlayId(scheduledPlayId);
        scheduledPlayRepository.deleteById(scheduledPlayId);
        log.info("END | deleteScheduledPlay | Deleted scheduled play with id {}", scheduledPlayId);
    }


    public Reservation addReservationToScheduledPlay(Long scheduledPlayId, ReservationIn reservationIn) {
        log.info("START | addReservation");

        ScheduledPlay scheduledPlay = getScheduledPlayById(scheduledPlayId);

        if (scheduledPlay == null) {
            return null;
        }

        Reservation reservation = new Reservation();
        reservation.setScheduledPlay(scheduledPlay);
        reservation.setName(reservationIn.getName());
        reservation.setEmail(reservationIn.getEmail());
        reservation.setReservedSeats(reservationIn.getReservedSeats());
        reservationService.addReservation(reservation);

        scheduledPlay.getReservations().add(reservation);
        scheduledPlay.setTotalReservedSeats(scheduledPlay.getTotalReservedSeats() + reservationIn.getReservedSeats());
        scheduledPlayRepository.saveAndFlush(scheduledPlay);

        log.info("END | addReservation | Reservation added: {}", reservation);
        return reservation;
    }

}
