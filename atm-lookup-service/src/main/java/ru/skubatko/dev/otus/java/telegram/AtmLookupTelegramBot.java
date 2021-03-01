package ru.skubatko.dev.otus.java.telegram;

import ru.skubatko.dev.otus.java.client.AtmClient;
import ru.skubatko.dev.otus.java.config.TelegramBotProps;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AtmLookupTelegramBot extends TelegramLongPollingBot {

    private final TelegramBotProps props;
    private final AtmClient atmClient;

    @Override
    public String getBotUsername() {
        return props.getName();
    }

    @Override
    public String getBotToken() {
        return props.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.debug("Receive new Update. updateID: " + update.getUpdateId());

        val chatId = update.getMessage().getChatId().toString();
        String inputText = update.getMessage().getText();

        if (inputText.startsWith("/start")) {
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText("Hello. This is start message");
            val atmsStatus = atmClient.getAtms();
            log.trace("onUpdateReceived() - trace: number of atms = {}", atmsStatus.size());
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
