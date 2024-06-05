package com.example.theater.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;


import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class TheaterPlayFullOut extends TheaterPlayOut {

    private List<ScheduledPlayOut> scheduledPlays;

    public TheaterPlayFullOut(TheaterPlayOut theaterPlayOut, List<ScheduledPlayOut> scheduledPlays) {
        super(theaterPlayOut.getId(),
                theaterPlayOut.getTitle(),
                theaterPlayOut.getPremiere());
        this.scheduledPlays = scheduledPlays;
    }

}
