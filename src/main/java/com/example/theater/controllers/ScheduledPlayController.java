package com.example.theater.controllers;

import com.example.theater.converters.ReservationConverter;
import com.example.theater.converters.ScheduledPlayConverter;
import com.example.theater.dto.ReservationIn;
import com.example.theater.dto.ScheduledPlayFullOut;
import com.example.theater.dto.ScheduledPlayIn;
import com.example.theater.dto.ScheduledPlayOut;
import com.example.theater.entities.Reservation;
import com.example.theater.entities.ScheduledPlay;
import com.example.theater.services.ScheduledPlayService;
import com.example.theater.services.TheaterPlayService;
import com.example.theater.validations.ValidationError;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin(originPatterns = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/scheduled-plays")
public class ScheduledPlayController {

    private final ScheduledPlayService scheduledPlayService;
    private final TheaterPlayService theaterPlayService;

    @GetMapping
    public ResponseEntity<List<ScheduledPlayOut>> getScheduledPlays() {
        log.info("Request getScheduledPlays received");
        List<ScheduledPlay> scheduledPlays = scheduledPlayService.getAllScheduledPlay();
        if (scheduledPlays == null) {
            log.info("getScheduledPlays NOT FOUND");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (scheduledPlays.isEmpty()) {
            log.info("getScheduledPlays NO CONTENT");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        log.info("Found {} scheduled plays", scheduledPlays.size());
        return ResponseEntity.status(HttpStatus.OK)
                             .body(ScheduledPlayConverter.convertEntityListToScheduledPlayOutList(scheduledPlays));
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'COORDINATOR')")
    @PostMapping
    public ResponseEntity<Object> addScheduledPlay(@Valid @RequestBody ScheduledPlayIn scheduledPlayIn,
                                                   BindingResult bindingResult) {
        log.info("Request addScheduledPlay received");
        log.debug("scheduledPlayIn: {}", scheduledPlayIn);
        if (bindingResult.hasErrors()) {
            log.warn("Validation error for POST addScheduledPlay: {}", bindingResult.getFieldErrors());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(new ValidationError(bindingResult.getFieldErrors()));
        }
        ScheduledPlay scheduledPlay = ScheduledPlayConverter.convertScheduledPlayInToEntity(scheduledPlayIn);
        scheduledPlay.setTheaterPlay(theaterPlayService.getPlayById(scheduledPlayIn.getTheaterPlayId()));

        ScheduledPlayOut scheduledPlayOut = ScheduledPlayConverter.convertEntityToScheduledPlayOut(
                scheduledPlayService.addScheduledPlay(scheduledPlay));
        log.debug("Added scheduled play: {}", scheduledPlayOut);
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduledPlayOut);
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'COORDINATOR')")
    @PatchMapping(value = "/{id}")
    public ResponseEntity<Object> updateScheduledPlayById(@PathVariable Long id,
                                                          @RequestBody ScheduledPlayIn scheduledPlayIn) {
        log.info("Request updateScheduledPlayById received");
        log.debug("Updating with scheduledPlayIn for update: {}", scheduledPlayIn);

        if (scheduledPlayIn.getAddress() == null && scheduledPlayIn.getDate() == null &&
                scheduledPlayIn.getTime() == null && scheduledPlayIn.getTotalSeats() == 0) {
            log.warn("Validation error for PATCH updateScheduledPlayById");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No content found for the update");
        }
        ScheduledPlayOut scheduledPlayOut = ScheduledPlayConverter.convertEntityToScheduledPlayOut(
                scheduledPlayService.updateScheduledPlay(id, scheduledPlayIn));
        log.debug("Updated scheduled play: {}", scheduledPlayOut);
        return ResponseEntity.status(HttpStatus.OK).body(scheduledPlayOut);
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'COORDINATOR')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteScheduledPlayById(@PathVariable Long id) {
        log.info("Request deleteScheduledPlay received");
        if (scheduledPlayService.getScheduledPlayById(id) == null) {
            log.info("Scheduled play with id {} not found", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        try {
            scheduledPlayService.deleteScheduledPlayById(id);
        } catch (Exception e) {
            log.error("Error while deleting scheduled play with id {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
        log.info("Scheduled play with id {} deleted", id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'COORDINATOR')")
    @GetMapping(value = "/{id}/reservations")
    public ResponseEntity<ScheduledPlayFullOut> getReservationListByScheduledPlay(@PathVariable Long id) {
        log.info("Request getReservationListByScheduledPlay received");
        ScheduledPlay scheduledPlay = scheduledPlayService.getScheduledPlayById(id);
        if (scheduledPlay == null) {
            log.info("getReservationListByScheduledPlay NOT FOUND");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        log.info("Found scheduled play: {}", scheduledPlay);
        return ResponseEntity.status(HttpStatus.OK)
                             .body(ScheduledPlayConverter.convertEntityToScheduledPlayFullOut(scheduledPlay));
    }

    @PostMapping(value = "/{id}/reservations")
    public ResponseEntity<Object> addReservationToScheduledPlayById(@Valid
                                                                    @RequestBody ReservationIn reservationIn,
                                                                    BindingResult bindingResult,
                                                                    @PathVariable Long id) {
        log.info("Request addReservationToScheduledPlayById received");
        if (bindingResult.hasErrors()) {
            log.warn("Validation error for POST addReservationToScheduledPlayById: {}", bindingResult.getFieldErrors());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(new ValidationError(bindingResult.getFieldErrors()));
        }

        ScheduledPlay scheduledPlay = scheduledPlayService.getScheduledPlayById(id);

        if (scheduledPlay == null) {
            log.info("ScheduledPlay with id {} not found", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        int availableSeats = scheduledPlay.getTotalSeats() - scheduledPlay.getTotalReservedSeats();

        if (availableSeats - reservationIn.getReservedSeats() < 0) {
            log.info("Reservation of {} is not available. Only {} seats left", reservationIn.getReservedSeats(),
                    availableSeats);
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }

        Reservation reservation = scheduledPlayService.addReservationToScheduledPlay(id, reservationIn);

        log.debug("Added reservation: {}", reservation);
        return ResponseEntity.status(HttpStatus.OK)
                             .body(ReservationConverter.convertEntityToReservationOut(reservation));
    }

}

