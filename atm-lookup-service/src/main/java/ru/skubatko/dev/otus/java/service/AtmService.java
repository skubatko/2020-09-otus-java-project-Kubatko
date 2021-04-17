package ru.skubatko.dev.otus.java.service;

import ru.skubatko.dev.otus.java.client.AtmClient;
import ru.skubatko.dev.otus.java.dto.AtmDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AtmService {

    private final AtmClient client;

    private static final String ATM_CASH_SERVICE_ON = "Y";

    @Cacheable(value = "atms")
    public List<AtmDto> getCashOutAtms() {
        log.trace("getCashOutAtms() - start");

        val atms = client.getAtms();
        log.trace("getCashOutAtms() - trace: total number of atms found = {}", atms.size());

        List<AtmDto> cashOutAtms =
            atms.stream()
                .filter(atmDto ->
                            ATM_CASH_SERVICE_ON.equals(atmDto.getServices().getCardCashOut())
                                && ATM_CASH_SERVICE_ON.equals(atmDto.getServices().getCashOut()))
                .collect(Collectors.toList());

        log.trace("getCashOutAtms() - end: number of atms with cash-out = {}", cashOutAtms.size());
        return cashOutAtms;
    }

    @CacheEvict(value = "atms")
    public void clearAtmsCache() { }
}
