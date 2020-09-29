package com.dexsys.TelegramBotDexsys;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
@Slf4j
public class TelegramBotDexsysApplication {

	public static void main(String[] args) {
		SpringApplication.run(TelegramBotDexsysApplication.class, args);
		log.info("Loggs");
		ModelGetSet modelGetSet = new ModelGetSet();
		modelGetSet.getName();

		ModelCounstructors modelCounstructors = new ModelCounstructors("LastName", 835345);
		System.out.println(modelCounstructors.getLastName() + " " +
				modelCounstructors.getPhoneNumber());
	}
}
