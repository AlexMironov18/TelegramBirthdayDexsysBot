package com.dexsys.TelegramBotDexsys.domain.services;

import com.dexsys.TelegramBotDexsys.app.clientService.mockClient.mockDTO.UserMockDTO;
import com.dexsys.TelegramBotDexsys.app.web.webDTO.UserWebDTO;
import com.dexsys.TelegramBotDexsys.services.IMockClient;
import com.dexsys.TelegramBotDexsys.services.IWebProxyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class WebProxyService implements IWebProxyService {

    @Autowired
    private IMockClient mockClient;

    @Override
    public UserWebDTO getUser(UUID uuid) {
        return mapperToWeb.apply(mockClient.getUser(uuid));
    }

    @Override
    public List<UserWebDTO> getUsers() {
        return mockClient.getAll().stream().map(mapperToWeb).collect(Collectors.toList());
    }

    @Override
    public UserMockDTO createUser(UserWebDTO userWebDTO) {
        return mockClient.createUser(mapperToMock.apply(userWebDTO));
    }

    @Override
    public UserMockDTO generateUser() {
        return mockClient.generateUser();
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
    ;
    //get generate delete getAll
    //эти методы вызываются из TelegramController и вызывают методы MockClient
}
