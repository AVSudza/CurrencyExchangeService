package ru.skillbox.currency.exchange.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.skillbox.currency.exchange.dto.CurrencyDto;
import ru.skillbox.currency.exchange.dto.ValCurs;
import ru.skillbox.currency.exchange.dto.Valute;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UpdateCurrencyRateService implements Runnable {
    private final String uri;
    private final String uriReserve;
    private HttpURLConnection connection = null;
    private final CurrencyService currencyService;
    private ValCurs valCurs;

    public UpdateCurrencyRateService(@Value("${source-currencies.cbr.api.get-currencies}") String uri,
                                     @Value("${source-currencies.cbr-xml-daily.api.get-currencies}") String uriReserve,
                                     CurrencyService currencyService) {
        this.uri = uri;
        this.uriReserve = uriReserve;
        this.currencyService = currencyService;
    }

    @Override
    public void run() {
        URL url;
        URL urlReserve;
        try {
            url = new URL(uri);
            urlReserve = new URL(uriReserve);
        } catch (MalformedURLException e) {
            log.info("Ошибка создания URL");
            return;
        }
        InputStream inputStream = getInputStream(url, urlReserve);
        valCurs = getListCurrencies(inputStream);
        updateCurrencyRate();

        try {
            Thread.sleep(3_600_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        new Thread(new UpdateCurrencyRateService(uri, uriReserve, currencyService)).start();
    }

    private InputStream getInputStream(URL mainUrl, URL reserveUrl) {
        InputStream inputStream = null;
        try {
            connection = (HttpURLConnection) mainUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/xml");
            inputStream = connection.getInputStream();
        } catch (IOException e) {
            try {
                connection = (HttpURLConnection) reserveUrl.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept", "application/xml");
                inputStream = connection.getInputStream();
            } catch (IOException ex) {
                log.info("Ошибка подключения к удаленному источнику");
            }
        }
        return inputStream;
    }

    private ValCurs getListCurrencies(InputStream inputStream) {
        JAXBContext jaxbContext;
        ValCurs valCurs;
        try {
            jaxbContext = JAXBContext.newInstance(ValCurs.class);
            valCurs = (ValCurs) jaxbContext.createUnmarshaller().unmarshal(inputStream);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        if (this.connection != null) {
            this.connection.disconnect();
        }
        log.info("Список валют, полученных из внешнего источника: " + valCurs.toString());
        return valCurs;
    }

    private void updateCurrencyRate() {

        List<String> listCharCode = currencyService.getAllCharCode();

        List<Valute> valuteContains = valCurs.getValuteList().stream()
                .filter(s -> listCharCode.contains(s.getCharCode()))
                .collect(Collectors.toList());
        valuteContains.forEach(currencyService::update);
        log.info("Обновленных записей: " + valuteContains.size());

        List<Valute> valuteNonContains = valCurs.getValuteList().stream()
                .filter(s -> !listCharCode.contains(s.getCharCode()))
                .collect(Collectors.toList());
        valuteNonContains.forEach(valute -> currencyService.create(
                new CurrencyDto(null,
                        valute.getValuteId(),
                        valute.getName(),
                        valute.getNominal(),
                        Double.valueOf(valute.getValue().replace(',', '.')),
                        valute.getNumCode(),
                        valute.getCharCode()))
        );
        log.info("Добавлено новых записей: " + valuteNonContains.size());
    }
}
