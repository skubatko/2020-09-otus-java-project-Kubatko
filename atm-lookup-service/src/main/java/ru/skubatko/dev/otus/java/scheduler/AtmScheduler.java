package ru.skubatko.dev.otus.java.scheduler;

import ru.skubatko.dev.otus.java.service.AtmService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AtmScheduler {

    private final AtmService service;

    @Scheduled(fixedRateString = "${app.cache.refreshRateMs}")
    public void refreshCache() {
        log.debug("refreshCache() - start");

        service.clearAtmsCache();
        log.trace("refreshCache() - trace: atms cache cleared");

        service.getCashOutAtms();
        log.trace("refreshCache() - trace: atms cache populated");

        log.debug("refreshCache() - end");
    }
}
