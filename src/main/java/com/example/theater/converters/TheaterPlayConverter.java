package com.example.theater.converters;

import com.example.theater.dto.TheaterPlayFullOut;
import com.example.theater.dto.TheaterPlayOut;
import com.example.theater.entities.TheaterPlay;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TheaterPlayConverter {

    private TheaterPlayConverter() {
    }

    public static TheaterPlayOut convertEntityToTheaterPlayOut(TheaterPlay theaterPlay) {
        log.debug("START: convertEntityToTheaterPlayOut {}", theaterPlay);
        TheaterPlayOut theaterPlayOut = null;
        if (theaterPlay != null) {
            theaterPlayOut = new TheaterPlayOut();
            theaterPlayOut.setId(theaterPlay.getId());
            theaterPlayOut.setTitle(theaterPlay.getTitle());
            theaterPlayOut.setPremiere(theaterPlay.getPremiere());
        }
        log.debug("END: convertEntityToTheaterPlayOut {}", theaterPlayOut);
        return theaterPlayOut;
    }


    public static TheaterPlayFullOut convertEntityToTheaterPlayFullOut(TheaterPlay theaterPlay) {
        log.debug("START: convertEntityToTheaterPlayFullOut {}", theaterPlay);
        if (theaterPlay == null) {
            log.debug("END: convertEntityToTheaterPlayFullOut: theaterPlay is null");
            return null;
        }
        log.debug("END: convertEntityToTheaterPlayFullOut");
        return new TheaterPlayFullOut(
                convertEntityToTheaterPlayOut(theaterPlay),
                ScheduledPlayConverter.convertEntityListToScheduledPlayOutList(theaterPlay.getScheduledPlays()));
    }

    public static List<TheaterPlayOut> convertEntityListToTheaterPlayOutList(List<TheaterPlay> theaterPlays) {
        log.debug("START: convertEntityListToTheaterPlayOutList");
        List<TheaterPlayOut> theaterPlaysOut = null;
        if (theaterPlays != null) {
            theaterPlaysOut = new ArrayList<>();
            for (TheaterPlay theaterPlay : theaterPlays) {
                theaterPlaysOut.add(convertEntityToTheaterPlayOut(theaterPlay));
            }
        }
        log.debug("END: convertEntityListToTheaterPlayOutList");
        return theaterPlaysOut;
    }
}
