package ru.skillbox.currency.exchange.service;

import ru.skillbox.currency.exchange.dto.CurrencyDto;
import ru.skillbox.currency.exchange.dto.CurrencyNamePriceDto;

import java.util.List;

public interface ICurrencyService {
    boolean exists(String isoCharCode);
    List<CurrencyNamePriceDto> getAllNamePriceDto();
    List<CurrencyDto> getAll();
    CurrencyDto getById(Long id);
    Double convertValue(Long value, Long numCode);
    CurrencyDto create(CurrencyDto dto);
}
