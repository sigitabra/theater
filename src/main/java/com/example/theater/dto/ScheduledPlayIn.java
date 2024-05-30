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

    @NotNull(message = "{validation.constraints.not.null.message}")
    private Long theaterPlayId;

    @NotBlank(message = "{validation.constraints.not.empty.message}")
    @Size(min = 1, max = 100, message = "{validation.constraints.size.message}")
    private String address;

    @NotNull(message = "{validation.constraints.not.null.message}")
    @Future(message = "{validation.constraints.future.message}")
    private Date date;

    @NotNull(message = "{validation.constraints.not.null.message}")
    private Time time;

    @NotNull(message = "{validation.constraints.not.null.message}")
    private int totalSeats;
}
