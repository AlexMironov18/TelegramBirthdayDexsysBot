package com.dexsys.TelegramBotDexsys;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.ApiContextInitializer;


@SpringBootApplication
@Slf4j
@EnableAutoConfiguration
public class TelegramBotDexsysApplication {

	public static ApplicationContext ctx;

	public static void main(String[] args) {

		ApiContextInitializer.init();
		ctx = SpringApplication.run(TelegramBotDexsysApplication.class, args);

	}
}
//make logs