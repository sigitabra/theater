package com.example.theater.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ReservationIn {

    @NotBlank(message = "{validation.constraints.not.empty.message}")
    @Size(min = 1, max = 50, message = "{validation.constraints.size.message}")
    private String name;

    @NotEmpty(message = "{validation.constraints.not.empty.message}")
    @Email(message = "{validation.constraints.email.message}")
    private String email;

    @Max(value = 3, message = "{validation.constraints.max.message}")
    @Min(value = 1, message = "{validation.constraints.min.message}")
    private int reservedSeats;

}
