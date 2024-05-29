package com.example.theater.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class TheaterPlayOut {

    private Long id;

    private String title;

    private Date premiere;
}
