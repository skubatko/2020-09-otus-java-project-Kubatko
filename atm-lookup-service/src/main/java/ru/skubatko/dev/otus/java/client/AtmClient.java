package ru.skubatko.dev.otus.java.client;

import ru.skubatko.dev.otus.java.config.AtmClientProps;
import ru.skubatko.dev.otus.java.dto.AtmDto;
import ru.skubatko.dev.otus.java.dto.AtmResponseDto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AtmClient {

    private final RestTemplate atmRestTemplate;
    private final AtmClientProps atmClientProps;
    private final ObjectMapper objectMapper;

    public List<AtmDto> getAtms() {
        try {
            val response =
                    atmRestTemplate.exchange(
                            atmClientProps.getUrl() + "/atms",
                            HttpMethod.GET, new HttpEntity<>(buildHeaders()),
                            String.class);

            val root = objectMapper.readTree(response.getBody());
            boolean isSuccess = root.get("success").asBoolean();
            if (isSuccess) {
                return objectMapper.readValue(root.get("data").toString(), new TypeReference<AtmResponseDto>() {}).getAtms();
            }
        } catch (JsonProcessingException ex) {
            log.warn("getAtms() - warn: cannot process response", ex);
        }
        return Collections.emptyList();
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("x-ibm-client-id", atmClientProps.getId());
        return headers;
    }
}
