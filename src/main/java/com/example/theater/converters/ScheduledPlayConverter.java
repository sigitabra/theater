package com.example.theater.converters;

import com.example.theater.dto.ScheduledPlayOut;
import com.example.theater.dto.ScheduledPlayFullOut;
import com.example.theater.entities.ScheduledPlay;

import java.util.ArrayList;
import java.util.List;

public class ScheduledPlayConverter {

    private ScheduledPlayConverter() {
    }


    public static ScheduledPlayOut convertEntityToScheduledPlayOut(ScheduledPlay scheduledPlay) {
        ScheduledPlayOut scheduledPlayOut = null;
        if (scheduledPlay != null) {
            scheduledPlayOut = new ScheduledPlayOut();
            scheduledPlayOut.setId(scheduledPlay.getId());
            scheduledPlayOut.setTitle(scheduledPlay.getTheaterPlay().getTitle());
            scheduledPlayOut.setAddress(scheduledPlay.getAddress());
            scheduledPlayOut.setDate(scheduledPlay.getDate());
            scheduledPlayOut.setTime(scheduledPlay.getTime());
            scheduledPlayOut.setAvailableSeats(scheduledPlay.getTotalSeats() - scheduledPlay.getTotalReservedSeats());
        }
        return scheduledPlayOut;
    }


    public static ScheduledPlayFullOut convertEntityToScheduledPlayFullOut(ScheduledPlay scheduledPlay) {
        return new ScheduledPlayFullOut(convertEntityToScheduledPlayOut(scheduledPlay),
                ReservationConverter.convertEntityListToReservationOutList(scheduledPlay.getReservations()));
    }


    public static List<ScheduledPlayOut> convertEntityListToScheduledPlayOutList(List<ScheduledPlay> scheduledPlays) {
        List<ScheduledPlayOut> scheduledPlaysOut = null;
        if (scheduledPlays != null) {
            scheduledPlaysOut = new ArrayList<>();
            for (ScheduledPlay scheduledPlay : scheduledPlays) {
                scheduledPlaysOut.add(convertEntityToScheduledPlayOut(scheduledPlay));
            }
        }
        return scheduledPlaysOut;
    }
}
