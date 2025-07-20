package ru.skillbox.currency.exchange;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.skillbox.currency.exchange.service.UpdateCurrencyRateService;

@Component
public class RunAfterStartup {
    private final UpdateCurrencyRateService updateCurrencyRateService;

    @Autowired
    public RunAfterStartup(UpdateCurrencyRateService service) {
        this.updateCurrencyRateService = service;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartUp() {
       new Thread(updateCurrencyRateService).start();
    }
}
