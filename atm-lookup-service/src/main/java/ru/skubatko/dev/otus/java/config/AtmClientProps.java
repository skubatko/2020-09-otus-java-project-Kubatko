package ru.skubatko.dev.otus.java.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConstructorBinding
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "app.atm-client")
public class AtmClientProps {

    private final String id;
    private final String url;
    private final Integer connectionTimeout;
    private final Integer readTimeout;
    private final String keystore;
}
