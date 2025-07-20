package ru.skillbox.currency.exchange.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyNamePriceDto {

    private String name;

    private Double value;

    @Override
    public String toString() {
        return "name: " + name + ", value: " + value;
    }
}