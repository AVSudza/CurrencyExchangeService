package ru.skillbox.currency.exchange.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.skillbox.currency.exchange.dto.CurrencyDto;
import ru.skillbox.currency.exchange.dto.CurrencyNamePriceDto;
import ru.skillbox.currency.exchange.dto.Valute;
import ru.skillbox.currency.exchange.entity.Currency;
import ru.skillbox.currency.exchange.mapper.CurrencyMapper;
import ru.skillbox.currency.exchange.repository.CurrencyRepository;

import java.math.BigInteger;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyService implements ICurrencyService {
    private final CurrencyMapper mapper;
    private final CurrencyRepository repository;

    @Override
    public boolean exists(String isoCharCode) {
        log.info("CurrencyService method exists executed");
        return repository.existsByIsoCharCode(isoCharCode);
    }

    public List<CurrencyNamePriceDto> getAllNamePriceDto() {
        log.info("CurrencyService method getAllNamePriceDto executed");
        List<Currency> currencyList = repository.findAll();
        return mapper.convertToCurrencyNamePriceDtoList(currencyList);
    }

    @Override
    public List<CurrencyDto> getAll() {
        log.info("CurrencyService method getAll executed");
        return mapper.convertToListDto(repository.findAll());
    }

    public List<BigInteger> getAllNumCode() {
        log.info("CurrencyService method getAllNumCode executed");
        return repository.getAllNumCode();
    }

    public List<String> getAllCharCode() {
        log.info("CurrencyService method getAllCharCode executed");
        return repository.getAllCharCode();
    }

    @Override
    public CurrencyDto getById(Long id) {
        log.info("CurrencyService method getById executed");
        return mapper.convertToDto(repository.findById(id).orElse(null)) ;
    }

    public CurrencyDto getByIsoCharCode(String isoCharCode) {
        log.info("CurrencyService method getById executed");
        Currency currency = repository.findByIsoCharCode(isoCharCode);
        return mapper.convertToDto(currency);
    }

    public Double convertValue(Long value, Long numCode) {
        log.info("CurrencyService method convertValue executed");
        Currency currency = repository.findByIsoCharCode(String.valueOf(numCode));
        return value * currency.getValue();
    }

    public CurrencyDto create(CurrencyDto dto) {
        log.info("CurrencyService method create executed");
        return mapper.convertToDto(repository.save(mapper.convertToEntity(dto)));
    }

    public Valute update(Valute valute) {
        log.info("CurrencyService method update executed");
        if (exists(valute.getCharCode())) {
            CurrencyDto currencyDto = getByIsoCharCode(valute.getCharCode());
            currencyDto.setNominal(valute.getNominal());
            currencyDto.setValue(Double.valueOf(valute.getValue().replace(",", ".")));
            repository.save(mapper.convertToEntity(currencyDto));
        }
        return valute;
    }
}
