package ru.skubatko.dev.otus.java.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtmResponseDto {

    @JsonProperty("atms")
    private List<AtmDto> atms = new ArrayList<>();
}
