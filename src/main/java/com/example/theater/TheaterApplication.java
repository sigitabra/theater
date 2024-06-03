package com.example.theater;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TheaterApplication {

    public static void main(String[] args) {
        SpringApplication.run(TheaterApplication.class, args);
    }

}
