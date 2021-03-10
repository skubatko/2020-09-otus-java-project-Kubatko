package ru.skubatko.dev.otus.java.telegram;

import ru.skubatko.dev.otus.java.config.TelegramBotProps;
import ru.skubatko.dev.otus.java.telegram.handler.MessageHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AtmLookupTelegramBot extends TelegramLongPollingBot {

    private final TelegramBotProps props;
    private final List<MessageHandler> handlers;

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
        try {
            log.debug("onUpdateReceived() - start: updateID = {}", update.getUpdateId());

            Message receivedMessage = update.getMessage();
            val chatId = receivedMessage.getChatId().toString();

            SendMessage message = new SendMessage();
            message.setChatId(chatId);

            val sendText = getSendText(receivedMessage);
            log.trace("onUpdateReceived() - trace: sendText = {}", sendText);
            message.setText(sendText);

            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        log.debug("onUpdateReceived() - end");
    }

    private String getSendText(Message receivedMessage) {
        return handlers.stream()
                .map(handler -> handler.handle(receivedMessage))
                .filter(StringUtils::isNotBlank)
                .findAny()
                .orElse(StringUtils.EMPTY);
    }
}
