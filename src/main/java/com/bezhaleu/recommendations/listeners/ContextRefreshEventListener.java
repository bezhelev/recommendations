package com.bezhaleu.recommendations.listeners;

import com.bezhaleu.recommendations.repositories.CsvRepository;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ContextRefreshEventListener {

    private final CsvRepository baseCsvRepositories;

    public ContextRefreshEventListener(CsvRepository baseCsvRepositories) {
        this.baseCsvRepositories = baseCsvRepositories;
    }

    @EventListener
    public void handleContextRefreshEvent(ContextRefreshedEvent contextRefreshedEvent) {
        baseCsvRepositories.fillCryptos();
    }
}
