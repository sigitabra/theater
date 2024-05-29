package com.example.theater.converters;

import com.example.theater.dto.TheaterPlayOut;
import com.example.theater.entities.TheaterPlay;

import java.util.ArrayList;
import java.util.List;

public class TheaterPlayConverter {

    private TheaterPlayConverter() {
    }


    public static TheaterPlayOut convertEntityToTheaterPlayOut(TheaterPlay theaterPlay) {
        TheaterPlayOut theaterPlayOut = null;
        if (theaterPlay != null) {
            theaterPlayOut = new TheaterPlayOut();
            theaterPlayOut.setId(theaterPlay.getId());
            theaterPlayOut.setTitle(theaterPlay.getTitle());
            theaterPlayOut.setPremiere(theaterPlay.getPremiere());
        }
        return theaterPlayOut;
    }


    public static List<TheaterPlayOut> convertEntityListToTheaterPlayOutList(List<TheaterPlay> theaterPlays) {
        List<TheaterPlayOut> theaterPlaysOut = null;
        if (theaterPlays != null) {
            theaterPlaysOut = new ArrayList<>();
            for (TheaterPlay theaterPlay : theaterPlays) {
                theaterPlaysOut.add(convertEntityToTheaterPlayOut(theaterPlay));
            }
        }
        return theaterPlaysOut;
    }
}
