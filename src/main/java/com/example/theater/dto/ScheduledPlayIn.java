package com.example.theater.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.sql.Date;
import java.sql.Time;


@Data
public class ScheduledPlayIn {

    @NotNull
    private Long theaterPlayId;

    @NotBlank(message = "{validation.constraints.not.empty.message}")
    @Size(min = 1, max = 100, message = "{validation.constraints.size.message}")
    private String address;

    @NotNull
    @Future(message = "{validation.constraints.future.message}")
    private Date date;

    @NotNull
    private Time time;

    @NotNull
    private int totalSeats;
}
