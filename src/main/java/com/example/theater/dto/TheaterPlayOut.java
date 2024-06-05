package com.example.theater.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TheaterPlayOut {

    private Long id;

    private String title;

    private Date premiere;
}
