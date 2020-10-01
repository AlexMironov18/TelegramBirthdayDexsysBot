package com.dexsys.TelegramBotDexsys;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;


@SpringBootApplication
@Slf4j
@EnableAutoConfiguration
public class TelegramBotDexsysApplication {

	public static void main(String[] args) throws TelegramApiRequestException {
		SpringApplication.run(TelegramBotDexsysApplication.class, args);
		ApiContextInitializer.init();
		TelegramBotsApi botsApi = new TelegramBotsApi();
		botsApi.registerBot(new RepeaterHandler());
	}
}
