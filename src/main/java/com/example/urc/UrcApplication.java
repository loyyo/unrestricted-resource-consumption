package com.example.urc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class UrcApplication {

    public static void main(String[] args) {
        SpringApplication.run(UrcApplication.class, args);
    }

}
