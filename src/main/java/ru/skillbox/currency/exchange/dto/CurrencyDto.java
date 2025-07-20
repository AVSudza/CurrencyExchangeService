package ru.skillbox.currency.exchange.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyDto {
    private Long id;

    private String valuteId;

    private String name;

    private int nominal;

    private Double value;

    private String isoNumCode;

    private String isoCharCode;

    @Override
    public String toString() {
        return "id: " + id +
                ", name: " + name +
                ", nominal: " + nominal +
                ", value: " + value +
                ", isoNumCode: " + isoNumCode +
                ", isoLetterCode: " + isoCharCode;
    }
}