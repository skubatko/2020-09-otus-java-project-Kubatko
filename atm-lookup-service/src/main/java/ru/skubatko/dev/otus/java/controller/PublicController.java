package ru.skubatko.dev.otus.java.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicController {

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}
