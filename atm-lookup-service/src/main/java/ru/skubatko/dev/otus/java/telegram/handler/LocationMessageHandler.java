package ru.skubatko.dev.otus.java.telegram.handler;

import static java.lang.Math.asin;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

import ru.skubatko.dev.otus.java.config.MapProps;
import ru.skubatko.dev.otus.java.dto.AtmDto;
import ru.skubatko.dev.otus.java.service.AtmService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
@Component
@RequiredArgsConstructor
public class LocationMessageHandler implements MessageHandler {

    private final AtmService atmService;
    private final MapProps mapProps;

    private static final double EARTH_RADIUS = 6371;

    @Override
    public String handle(Message message) {
        if (message.hasLocation()) {
            val userLon = message.getLocation().getLongitude();
            val userLat = message.getLocation().getLatitude();
            log.trace("handle() - trace: location: longitude = {}, latitude = {}", userLon, userLat);

            val nearest =
                atmService.getCashOutAtms().stream()
                    .map(AtmDto::getCoordinates)
                    .min((o1, o2) -> {
                        val o1Lon = o1.getLongitude();
                        val o1Lat = o1.getLatitude();
                        val o2Lon = o2.getLongitude();
                        val o2Lat = o2.getLatitude();
                        val o1distance = getDistance(o1Lon, o1Lat, userLon, userLat);
                        val o2distance = getDistance(o2Lon, o2Lat, userLon, userLat);
                        return (int) (o1distance - o2distance);
                    });

            if (nearest.isEmpty()) {
                return StringUtils.EMPTY;
            }

            val nearestCoordinates = nearest.get();
            val longitude = nearestCoordinates.getLongitude();
            val latitude = nearestCoordinates.getLatitude();
            return String.format(mapProps.getUrl(), longitude, latitude);
        }

        return StringUtils.EMPTY;
    }

    private double getDistance(double lon1, double lat1, double lon2, double lat2) {
        double sin1 = sin((lat1 - lat2) / 2);
        double sin2 = sin((lon1 - lon2) / 2);
        return 2 * EARTH_RADIUS * asin(sqrt(sin1 * sin1 + sin2 * sin2 * cos(lat1) * cos(lat2)));
    }
}
