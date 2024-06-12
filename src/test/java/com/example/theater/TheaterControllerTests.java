package com.example.theater;

import com.example.theater.dto.TheaterPlayIn;
import com.example.theater.entities.TheaterPlay;
import com.example.theater.services.TheaterPlayService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class TheaterControllerTests {

    @Autowired
    private TheaterPlayService theaterPlayService;

    @BeforeEach
    void setUpTheaterPlayService() {
        TheaterPlayIn t1 = new TheaterPlayIn();
        TheaterPlayIn t2 = new TheaterPlayIn();
        TheaterPlayIn t3 = new TheaterPlayIn();
        t1.setTitle("Hamlet");
        t2.setTitle("Macbeth");
        t3.setTitle("Othello");
        t1.setPremiere(Date.valueOf("2023-01-15"));
        t2.setPremiere(Date.valueOf("2023-02-20"));
        t3.setPremiere(Date.valueOf("2023-03-25"));

        theaterPlayService.addTheaterPlay(t1);
        theaterPlayService.addTheaterPlay(t2);
        theaterPlayService.addTheaterPlay(t3);
    }

    @Test
    void getTheaterPlays() {
        List<TheaterPlay> theaterPlays = theaterPlayService.getAllPlays();
        assertEquals(3, theaterPlays.size());
    }

    @Test
    void getTheaterPlay() {
        TheaterPlay theaterPlays = theaterPlayService.getPlayById(1L);
        assertEquals("Hamlet", theaterPlays.getTitle());
    }

}
