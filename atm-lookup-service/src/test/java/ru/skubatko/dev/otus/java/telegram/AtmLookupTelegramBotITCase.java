package ru.skubatko.dev.otus.java.telegram;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ru.skubatko.dev.otus.java.client.AtmClient;
import ru.skubatko.dev.otus.java.dto.AtmDto;

import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Collections;

@DisplayName("Телеграм бот поиска банкомата")
@Tag("it")
@ActiveProfiles("it")
@SpringBootTest
class AtmLookupTelegramBotITCase {

    @SpyBean
    private AtmLookupTelegramBot bot;
    @MockBean
    private AtmClient atmClient;

    @Captor
    private ArgumentCaptor<SendMessage> sendMessageCaptor;

    @DisplayName("должен отправлять ожидаемые координаты ближайшего банкомата")
    @SneakyThrows
    @Test
    void shouldSendExpectedCoordinatesOfTheNearestAtm() {
        val location = new Location();
        location.setLatitude(37.348);
        location.setLongitude(55.845169);

        val message = new Message();
        message.setLocation(location);
        message.setChat(new Chat(1L, "test-type"));

        val update = new Update();
        update.setMessage(message);

        val atm = new AtmDto();
        atm.setCoordinates(new AtmDto.Coordinates(37.351914, 55.852521));
        atm.setServices(new AtmDto.Services("Y", "Y"));

        when(atmClient.getAtms()).thenReturn(Collections.singletonList(atm));

        bot.onUpdateReceived(update);

        verify(bot).execute(sendMessageCaptor.capture());
        val sendMessage = sendMessageCaptor.getValue();

        assertThat(sendMessage.getText()).isEqualTo("https://yandex.ru/maps/?pt=55.852521,37.351914&z=18&l=map");
    }
}
