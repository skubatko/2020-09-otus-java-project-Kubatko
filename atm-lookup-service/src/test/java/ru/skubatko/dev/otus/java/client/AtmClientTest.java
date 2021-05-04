package ru.skubatko.dev.otus.java.client;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import ru.skubatko.dev.otus.java.config.AtmClientProps;

import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import java.nio.file.Files;

@DisplayName("Клиент поиска банкоматов")
@ActiveProfiles("test")
@SpringBootTest
class AtmClientTest {

    @MockBean
    private RestTemplate atmRestTemplate;

    @Autowired
    private AtmClientProps atmClientProps;
    @Autowired
    private AtmClient client;

    @DisplayName("должен возвращать ожидаемый список банкоматов")
    @SneakyThrows
    @Test
    void shouldReturnExpectedAtmList() {
        val file = ResourceUtils.getFile("classpath:data/atms-response-example.json");
        val responseBody = Files.readString(file.toPath());

        when(atmRestTemplate.exchange(
            eq(atmClientProps.getUrl() + "/atms"),
            eq(HttpMethod.GET),
            any(HttpEntity.class),
            eq(String.class))).thenReturn(new ResponseEntity<>(responseBody, HttpStatus.OK));

        val atms = client.getAtms();

        assertThat(atms).hasSize(3);
    }
}
