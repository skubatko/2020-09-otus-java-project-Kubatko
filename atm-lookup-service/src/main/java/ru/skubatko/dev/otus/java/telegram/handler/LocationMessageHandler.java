package ru.skubatko.dev.otus.java.telegram.handler;

import static java.lang.Math.asin;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

import ru.skubatko.dev.otus.java.client.AtmClient;
import ru.skubatko.dev.otus.java.dto.AtmDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class LocationMessageHandler implements MessageHandler {

    private final AtmClient atmClient;

    private static final String ATM_CASH_SERVICE_ON = "Y";

    @Override
    public String handle(Message message) {
        if (message.hasLocation()) {
            val userLon = message.getLocation().getLongitude();
            val userLat = message.getLocation().getLatitude();
            log.trace("handle() - trace: location: longitude = {}, latitude = {}", userLon, userLat);

            val atms = atmClient.getAtms();
            log.trace("handle() - trace: number of atms = {}", atms.size());

            val cashOutAtms = getCashOutAtms(atms);
            log.trace("handle() - trace: number of atms with cash-out = {}", cashOutAtms.size());

            val nearest = cashOutAtms.stream().min((o1, o2) -> {
                val o1Lon = o1.getCoordinates().getLongitude();
                val o1Lat = o1.getCoordinates().getLatitude();
                val o2Lon = o2.getCoordinates().getLongitude();
                val o2Lat = o2.getCoordinates().getLatitude();
                val o1distance = getDistance(o1Lon, o1Lat, userLon, userLat);
                val o2distance = getDistance(o2Lon, o2Lat, userLon, userLat);
                return (int) (o1distance - o2distance);
            });

            if (nearest.isEmpty()) {
                return "";
            }

            val nearestCoordinates = nearest.get().getCoordinates();
            return String.format("Found the nearest ATM on location: longitude = %f, latitude = %f",
                    nearestCoordinates.getLongitude(), nearestCoordinates.getLatitude());
        }

        return StringUtils.EMPTY;
    }

    private List<AtmDto> getCashOutAtms(List<AtmDto> atms) {
        return atms.stream()
                .filter(atmDto ->
                        ATM_CASH_SERVICE_ON.equals(atmDto.getServices().getCardCashOut())
                                && ATM_CASH_SERVICE_ON.equals(atmDto.getServices().getCashOut()))
                .collect(Collectors.toList());
    }

    private double getDistance(double lon1, double lat1, double lon2, double lat2) {
        double R = 6371;
        double sin1 = sin((lat1 - lat2) / 2);
        double sin2 = sin((lon1 - lon2) / 2);
        return 2 * R * asin(sqrt(sin1 * sin1 + sin2 * sin2 * cos(lat1) * cos(lat2)));
    }
}
