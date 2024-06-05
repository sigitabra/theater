package com.example.theater.converters;

import com.example.theater.dto.ScheduledPlayIn;
import com.example.theater.dto.ScheduledPlayOut;
import com.example.theater.dto.ScheduledPlayFullOut;
import com.example.theater.entities.ScheduledPlay;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ScheduledPlayConverter {

    private ScheduledPlayConverter() {
    }


    public static ScheduledPlayOut convertEntityToScheduledPlayOut(ScheduledPlay scheduledPlay) {
        log.debug("START: convertEntityToScheduledPlayOut {}", scheduledPlay);
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
        log.debug("END: convertEntityToScheduledPlayOut {}", scheduledPlayOut);
        return scheduledPlayOut;
    }


    public static ScheduledPlayFullOut convertEntityToScheduledPlayFullOut(ScheduledPlay scheduledPlay) {
        log.debug("START: convertEntityToScheduledPlayFullOut {}", scheduledPlay);
        if (scheduledPlay == null) {
            log.debug("END: convertEntityToScheduledPlayFullOut: scheduledPlay is null");
            return null;
        }
        log.debug("END: convertEntityToScheduledPlayFullOut");
        return new ScheduledPlayFullOut(
                convertEntityToScheduledPlayOut(scheduledPlay),
                ReservationConverter.convertEntityListToReservationOutList(scheduledPlay.getReservations()));
    }


    public static List<ScheduledPlayOut> convertEntityListToScheduledPlayOutList(List<ScheduledPlay> scheduledPlays) {
        log.debug("START: convertEntityListToScheduledPlayOutList");
        List<ScheduledPlayOut> scheduledPlaysOut = null;
        if (scheduledPlays != null) {
            scheduledPlaysOut = new ArrayList<>();
            for (ScheduledPlay scheduledPlay : scheduledPlays) {
                scheduledPlaysOut.add(convertEntityToScheduledPlayOut(scheduledPlay));
            }
        }
        log.debug("END: convertEntityListToScheduledPlayOutList");
        return scheduledPlaysOut;
    }

    public static ScheduledPlay convertScheduledPlayInToEntity(ScheduledPlayIn scheduledPlayIn) {
        log.debug("START: convertScheduledPlayInToEntity: {}", scheduledPlayIn);
        ScheduledPlay scheduledPlay = new ScheduledPlay();
        scheduledPlay.setAddress(scheduledPlayIn.getAddress());
        scheduledPlay.setDate(scheduledPlayIn.getDate());
        scheduledPlay.setTime(scheduledPlayIn.getTime());
        scheduledPlay.setTotalSeats(scheduledPlayIn.getTotalSeats());
        scheduledPlay.setTotalReservedSeats(0);
        scheduledPlay.setReservations(new ArrayList<>());
        log.debug("END: convertScheduledPlayInToEntity: {}", scheduledPlay);
        return scheduledPlay;
    }
}
