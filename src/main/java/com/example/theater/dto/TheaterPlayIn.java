package com.example.theater.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.sql.Date;

@Data
public class TheaterPlayIn {

    @NotBlank(message = "{validation.constraints.not.empty.message}")
    @Size(min = 1, max = 100, message = "{validation.constraints.size.message}")
    @Pattern(regexp = "^[^\\s].*$", message = "{validation.constraints.start.with.space.message}")
    @Pattern(regexp = "^.*[^\\s]$", message = "{validation.constraints.end.with.space.message}")
    @Pattern(regexp = "^((?! {2}).)*$", message = "{validation.constraints.consecutive.space.message}")
    private String title;

    @NotNull(message = "{validation.constraints.not.null.message}")
    private Date premiere;
}