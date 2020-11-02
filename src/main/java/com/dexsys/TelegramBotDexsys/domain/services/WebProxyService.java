package com.dexsys.TelegramBotDexsys.domain.services;

import com.dexsys.TelegramBotDexsys.app.clientService.mockClient.mockDTO.UserMockDTO;
import com.dexsys.TelegramBotDexsys.app.exceptions.ApiRequestException;
import com.dexsys.TelegramBotDexsys.app.web.webDTO.UserWebDTO;
import com.dexsys.TelegramBotDexsys.services.IMockClient;
import com.dexsys.TelegramBotDexsys.services.IWebProxyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class WebProxyService implements IWebProxyService {

    @Autowired
    private IMockClient mockClient;

    @Override
    public UserWebDTO getUser(UUID uuid) throws HttpStatusCodeException {
        return mapperToWeb.apply(mockClient.getUser(uuid));
    }

    @Override
    public List<UserWebDTO> getUsers() throws HttpStatusCodeException {
        return mockClient.getAll().stream().map(mapperToWeb).collect(Collectors.toList());
    }

    @Override
    public String createUser(UserWebDTO userWebDTO) throws HttpStatusCodeException {
        return mockClient.createUser(mapperToMock.apply(userWebDTO));
    }

    @Override
    public UserMockDTO generateUser() throws HttpStatusCodeException {
        return mockClient.generateUser();
    }

    @Override
    public Set<HttpMethod> getOptions(UUID uuid) throws HttpStatusCodeException {
        return mockClient.getOptions(uuid);
    }

    private Function<UserWebDTO, UserMockDTO> mapperToMock = it -> UserMockDTO.builder()
            .birthDate(it.getBirthDate())
            .chatId(it.getChatId())
            .firstName(it.getFirstName())
            .secondName(it.getSecondName())
            .middleName(it.getMiddleName())
            .id(it.getId())
            .isMale(it.isMale())
            .phone(it.getPhone())
            .build();
    private Function<UserMockDTO, UserWebDTO> mapperToWeb = it -> UserWebDTO.builder()
            .birthDate(it.getBirthDate())
            .chatId(it.getChatId())
            .firstName(it.getFirstName())
            .secondName(it.getSecondName())
            .middleName(it.getMiddleName())
            .id(it.getId())
            .isMale(it.isMale())
            .phone(it.getPhone())
            .build();
}
