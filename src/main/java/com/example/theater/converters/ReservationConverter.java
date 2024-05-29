package com.example.theater.converters;

import com.example.theater.dto.ReservationOut;
import com.example.theater.entities.Reservation;

import java.util.ArrayList;
import java.util.List;

public class ReservationConverter {

    private ReservationConverter() {
    }


    public static ReservationOut convertEntityToReservationOut(Reservation reservation) {
        ReservationOut reservationOut = null;
        if (reservation != null) {
            reservationOut = new ReservationOut();
            reservationOut.setId(reservation.getId());
            reservationOut.setName(reservation.getName());
            reservationOut.setEmail(reservation.getEmail());
            reservationOut.setReservedSeats(reservation.getReservedSeats());
        }
        return reservationOut;
    }


    public static List<ReservationOut> convertEntityListToReservationOutList(List<Reservation> reservations) {
        List<ReservationOut> reservationsOut = null;
        if (reservations != null) {
            reservationsOut = new ArrayList<>();
            for (Reservation reservation : reservations) {
                reservationsOut.add(convertEntityToReservationOut(reservation));
            }
        }
        return reservationsOut;
    }
}
