package ru.skubatko.dev.otus.java.config;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;

@Configuration
@RequiredArgsConstructor
public class AtmClientConfig {

    private final AtmClientProps props;

    @Bean
    public RestTemplate atmRestTemplate() {
        val requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient());
        requestFactory.setConnectionRequestTimeout(props.getConnectionTimeout());
        requestFactory.setConnectTimeout(props.getConnectionTimeout());
        requestFactory.setReadTimeout(props.getReadTimeout());
        return new RestTemplate(requestFactory);
    }

    public HttpClient httpClient() {
        return HttpClientBuilder.create()
                .setSSLContext(sslContext())
                .build();
    }

    @SneakyThrows
    private SSLContext sslContext() {
        val keystore = getClass().getClassLoader().getResource(props.getKeystore());
        return SSLContextBuilder.create()
                .loadKeyMaterial(keystore, "".toCharArray(), "".toCharArray())
                .build();
    }
}
