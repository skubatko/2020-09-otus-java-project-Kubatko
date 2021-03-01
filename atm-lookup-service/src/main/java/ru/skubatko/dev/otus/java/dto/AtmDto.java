package ru.skubatko.dev.otus.java.dto;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtmDto {

    @JsonProperty("coordinates")
    private Coordinates coordinates = new Coordinates();

    @JsonProperty("services")
    private Services services = new Services();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Coordinates {

        @JsonProperty("latitude")
        private String latitude = EMPTY;

        @JsonProperty("longitude")
        private String longitude = EMPTY;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Services {

        @JsonProperty("cardCashOut")
        private String cardCashOut = EMPTY;

        @JsonProperty("cashOut")
        private String cashOut = EMPTY;
    }
}