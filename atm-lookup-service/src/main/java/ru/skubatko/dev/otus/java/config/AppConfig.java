package ru.skubatko.dev.otus.java.config;


import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableCaching
@EnableScheduling
@ConfigurationPropertiesScan(basePackageClasses = AppConfig.class)
public class AppConfig {
}
