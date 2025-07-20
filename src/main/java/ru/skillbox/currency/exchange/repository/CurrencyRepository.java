package ru.skillbox.currency.exchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.skillbox.currency.exchange.entity.Currency;

import java.math.BigInteger;
import java.util.List;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    Currency findByIsoCharCode(String isoCharCode);

    boolean existsByIsoCharCode(String isoCharCode);

    @Query(value = "select iso_num_code from currency;" , nativeQuery = true)
    List<BigInteger> getAllNumCode();

    @Query(value = "select iso_char_code from currency;" , nativeQuery = true)
    List<String> getAllCharCode();
}
