package com.dexsys.TelegramBotDexsys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.telegram.telegrambots.ApiContextInitializer;


@SpringBootApplication(scanBasePackages = "com.dexsys.TelegramBotDexsys")
public class ATelegramBotDexsysApplication {

	public static void main(String[] args) {

		ApiContextInitializer.init();
		final ConfigurableApplicationContext ctx = SpringApplication.run(ATelegramBotDexsysApplication.class, args);

	}
}
//make logs