package com.example.theater.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ScheduledPlayFullOut extends ScheduledPlayOut {

    private List<ReservationOut> reservations;

    public ScheduledPlayFullOut(ScheduledPlayOut scheduledPlayOut, List<ReservationOut> reservations) {
        super(scheduledPlayOut.getId(),
                scheduledPlayOut.getTitle(),
                scheduledPlayOut.getAddress(),
                scheduledPlayOut.getDate(),
                scheduledPlayOut.getTime(),
                scheduledPlayOut.getAvailableSeats());
        this.reservations = reservations;
    }

}
