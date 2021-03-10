package ru.skubatko.dev.otus.java.telegram.handler;

import ru.skubatko.dev.otus.java.client.AtmClient;

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

    private final AtmClient atmClient;

    @Override
    public String handle(Message message) {
        if (message.hasLocation()) {
            val location = message.getLocation();
            log.trace("handle() - trace: location = {}", location);
            val atms = atmClient.getAtms();
            log.trace("handle() - trace: number of atms = {}", atms.size());
            return String.format("For location = %s found %d ATMs", location, atms.size());
        }

        return StringUtils.EMPTY;
    }
}
