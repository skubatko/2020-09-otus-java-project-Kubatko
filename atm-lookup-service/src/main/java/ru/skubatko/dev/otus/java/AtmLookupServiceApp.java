package ru.skubatko.dev.otus.java;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class AtmLookupServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(AtmLookupServiceApp.class, args);
    }
}
