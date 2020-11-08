package com.dexsys.TelegramBotDexsys.app.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfiguration {

    @Bean
    public RestOperations getRestTemplate(){
        return new RestTemplate();
    }
}
