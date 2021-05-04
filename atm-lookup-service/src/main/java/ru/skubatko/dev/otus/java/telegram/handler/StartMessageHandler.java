package ru.skubatko.dev.otus.java.telegram.handler;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class StartMessageHandler implements MessageHandler {

    @Override
    public String handle(Message message) {
        if (message.hasText() && message.getText().startsWith("/start")) {
            return "Hello! Please send your location to get the nearest ATM with cash-out.";
        }

        return StringUtils.EMPTY;
    }
}
