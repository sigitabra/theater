package com.example.theater.dto;

import lombok.Data;

@Data
public class ReservationOut {

    private Long id;

    private String name;

    private String email;

    private int reservedSeats;
}
