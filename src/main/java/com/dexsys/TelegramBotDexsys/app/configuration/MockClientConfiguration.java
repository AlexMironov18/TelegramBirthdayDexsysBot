package com.dexsys.TelegramBotDexsys.app.configuration;

import com.dexsys.TelegramBotDexsys.app.clientService.mockClient.MockClient;
import com.dexsys.TelegramBotDexsys.app.clientService.mockClient.MockClientDefault;
import com.dexsys.TelegramBotDexsys.services.IMockClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;


@Configuration
public class MockClientConfiguration {

    @Bean
    @ConditionalOnProperty(value = "useMock", havingValue = "true")
    public IMockClient useMockClient() {
        return new MockClient();
    }

    @Bean
    @ConditionalOnProperty(value = "useMock", havingValue = "false")
    public IMockClient useDefaultMockClient() {
        return new MockClientDefault();
    }
}
