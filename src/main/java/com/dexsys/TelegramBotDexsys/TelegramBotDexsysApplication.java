package com.dexsys.TelegramBotDexsys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.telegram.telegrambots.ApiContextInitializer;


@SpringBootApplication(scanBasePackages = "com.dexsys.TelegramBotDexsys")
public class TelegramBotDexsysApplication {

	public static void main(String[] args) {

		final ConfigurableApplicationContext ctx = SpringApplication.run(TelegramBotDexsysApplication.class, args);

	}
}
//make logs