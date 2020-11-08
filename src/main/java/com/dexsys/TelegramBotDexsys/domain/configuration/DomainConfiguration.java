package com.dexsys.TelegramBotDexsys.domain.configuration;

import com.dexsys.TelegramBotDexsys.app.clientService.telegramHandlers.RepeaterHandler;
import com.dexsys.TelegramBotDexsys.services.IRepeaterHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;

import javax.annotation.PostConstruct;

@Configuration
@Slf4j
public class DomainConfiguration {

    @Bean
    public IRepeaterHandler setupBot() {

        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();
        IRepeaterHandler handler = new RepeaterHandler();
        try {
            botsApi.registerBot((LongPollingBot) handler);
        } catch (TelegramApiException e) {
            log.error("setupBot: " + e.toString());
        }
        return handler;
    }
}
