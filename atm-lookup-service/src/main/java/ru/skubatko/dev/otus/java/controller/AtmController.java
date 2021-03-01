package ru.skubatko.dev.otus.java.controller;

import ru.skubatko.dev.otus.java.client.AtmClient;
import ru.skubatko.dev.otus.java.dto.AtmDto;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class AtmController {

    private final AtmClient atmClient;

    @GetMapping
    public List<AtmDto> getAll() throws JsonProcessingException {
        return atmClient.getAtms();
    }
}
