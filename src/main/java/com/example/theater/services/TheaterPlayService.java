package com.example.theater.services;

import com.example.theater.dto.TheaterPlayIn;
import com.example.theater.entities.TheaterPlay;
import com.example.theater.repositories.TheaterPlayRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class TheaterPlayService {

    private final TheaterPlayRepository theaterPlayRepository;

    public List<TheaterPlay> getAllPlays() {
        log.info("START | getAllPlays");
        List<TheaterPlay> theaterPlays = theaterPlayRepository.findAll();
        log.info("END | getAllPlays");
        return theaterPlays;
    }


    public TheaterPlay getPlayById(Long id) {
        log.info("START | getPlayById | Getting theaterPlay by id {}", id);
        TheaterPlay theaterPlay = theaterPlayRepository.findById(id).orElse(null);
        if (theaterPlay == null) {
            log.info("END | getPlayById | No theaterPlay found with id {}", id);
        } else {
            log.info("END | getPlayById | Returning theaterPlay {}", theaterPlay);
        }
        return theaterPlay;
    }


    public TheaterPlay addTheaterPlay(TheaterPlayIn theaterPlayIn) {
        log.info("START | addTheaterPlay");
        TheaterPlay theaterPlay = new TheaterPlay();
        theaterPlay.setTitle(theaterPlayIn.getTitle());
        theaterPlay.setPremiere(theaterPlayIn.getPremiere());
        log.info("END | addTheaterPlay | TheaterPlay added: {}", theaterPlay);
        return theaterPlayRepository.saveAndFlush(theaterPlay);
    }


    public TheaterPlay updateTheaterPlay(Long theaterPlayId, TheaterPlayIn theaterPlayIn) {
        log.info("START | updateTheaterPlay");
        TheaterPlay theaterPlay = getPlayById(theaterPlayId);
        if (theaterPlay == null) {
            log.info("END | updateTheaterPlay | No theaterPLay found with id {}", theaterPlayId);
            return null;
        }
        if (theaterPlayIn.getTitle() != null) {
            theaterPlay.setTitle(theaterPlayIn.getTitle());
        }
        if (theaterPlayIn.getPremiere() != null) {
            theaterPlay.setPremiere(theaterPlayIn.getPremiere());
        }
        log.info("END | updateTheaterPlay | TheaterPlay updated: {}", theaterPlay);
        return theaterPlayRepository.saveAndFlush(theaterPlay);
    }


    public void deleteTheaterPlayById(Long theaterPlayId) {
        log.info("START | deleteTheaterPlay");
        theaterPlayRepository.deleteById(theaterPlayId);
        log.info("END | deleteTheaterPlay | Deleted theaterPLay with id {}", theaterPlayId);
    }

}
