package ru.skubatko.dev.otus.java.telegram.handler;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface MessageHandler {

    String handle(Message message);
}
