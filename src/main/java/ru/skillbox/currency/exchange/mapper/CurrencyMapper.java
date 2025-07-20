package ru.skillbox.currency.exchange.mapper;

import org.mapstruct.Mapper;
import ru.skillbox.currency.exchange.dto.CurrencyDto;
import ru.skillbox.currency.exchange.dto.CurrencyNamePriceDto;
import ru.skillbox.currency.exchange.entity.Currency;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {

    CurrencyDto convertToDto(Currency currency);

    List<CurrencyDto> convertToListDto(List<Currency> currencyList);

    Currency convertToEntity(CurrencyDto currencyDto);

    List<Currency> convertToListEntity(List<CurrencyDto> currencyDtoList);

    CurrencyNamePriceDto convertToCurrencyNamePriceDto(Currency currency);

    List<CurrencyNamePriceDto> convertToCurrencyNamePriceDtoList(List<Currency> currencies);
}
