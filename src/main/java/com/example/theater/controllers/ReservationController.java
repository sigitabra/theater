package com.example.theater.controllers;

import com.example.theater.converters.ReservationConverter;
import com.example.theater.dto.ReservationOut;
import com.example.theater.entities.Reservation;
import com.example.theater.services.ReservationService;
import com.example.theater.services.ScheduledPlayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    private final ScheduledPlayService scheduledPlayService;

    @Operation(summary = "Get reservation details by id", description = "No authentication is required")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(hidden = true)))
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

    @Operation(summary = "Delete reservation by id", description = "No authentication is required")
    @ApiResponse(responseCode = "202", description = "Accepted", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "406", description = "Not Acceptable", content = @Content(schema = @Schema(hidden = true)))
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteReservationById(@PathVariable Long id) {
        log.info("Request deleteReservationById received");
        Reservation reservation = reservationService.getReservationById(id);
        if (reservation == null) {
            log.info("Reservation with id {} not found", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        try {
            scheduledPlayService.removeReservedSeats(reservation.getScheduledPlay(),reservation.getReservedSeats());
            reservationService.deleteReservationById(id);
        } catch (Exception e) {
            log.error("Error while deleting reservation with id {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
        log.info("Reservation with id {} deleted", id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

}
