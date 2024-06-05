package com.example.theater.converters;

import com.example.theater.dto.ReservationOut;
import com.example.theater.entities.Reservation;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ReservationConverter {

    private ReservationConverter() {
    }


    public static ReservationOut convertEntityToReservationOut(Reservation reservation) {
        log.debug("START: convertEntityToReservationOut {}", reservation);
        ReservationOut reservationOut = null;
        if (reservation != null) {
            reservationOut = new ReservationOut();
            reservationOut.setId(reservation.getId());
            reservationOut.setName(reservation.getName());
            reservationOut.setEmail(reservation.getEmail());
            reservationOut.setReservedSeats(reservation.getReservedSeats());
        }
        log.debug("END: convertEntityToReservationOut {}", reservationOut);
        return reservationOut;
    }


    public static List<ReservationOut> convertEntityListToReservationOutList(List<Reservation> reservations) {
        log.debug("START: convertEntityListToReservationOutList");
        List<ReservationOut> reservationsOut = null;
        if (reservations != null) {
            reservationsOut = new ArrayList<>();
            for (Reservation reservation : reservations) {
                reservationsOut.add(convertEntityToReservationOut(reservation));
            }
        }
        log.debug("END: convertEntityListToReservationOutList");
        return reservationsOut;
    }
}
