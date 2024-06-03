package com.example.theater.controllers;

import com.example.theater.converters.ReservationConverter;
import com.example.theater.dto.ReservationOut;
import com.example.theater.entities.Reservation;
import com.example.theater.services.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@CrossOrigin(originPatterns = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<ReservationOut> getReservation(@PathVariable Long id) {
        log.info("Request getReservations received");
        Reservation reservation = reservationService.getReservationById(id);
        if (reservation == null) {
            log.info("getReservations NOT FOUND");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        log.info("getReservations FOUND {}", reservation);
        return ResponseEntity.status(HttpStatus.OK)
                             .body(ReservationConverter.convertEntityToReservationOut(reservation));
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteReservationById(@PathVariable Long id) {
        log.info("Request deleteReservationById received");
        if (reservationService.getReservationById(id) == null) {
            log.info("Reservation with id {} not found", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        try {
            reservationService.deleteReservationById(id);
        } catch (Exception e) {
            log.error("Error while deleting reservation with id {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
        log.info("Reservation with id {} deleted", id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

}
