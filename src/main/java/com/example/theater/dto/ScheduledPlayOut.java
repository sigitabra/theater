package com.example.theater.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ScheduledPlayOut {

    private Long id;

    private String title;

    private String address;

    private Date date;

    private Time time;

    private int availableSeats;
}
