package com.dexsys.TelegramBotDexsys.services;

import com.dexsys.TelegramBotDexsys.app.clientService.mockClient.mockDTO.UserMockDTO;
import org.springframework.http.HttpMethod;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface IMockClient {
    //get generate delete getAll
    UserMockDTO getUser(UUID uuid);
    List<UserMockDTO> getAll();
    String createUser(UserMockDTO userMockDTO);
    UserMockDTO generateUser();
    Set<HttpMethod> getOptions(UUID uuid);
}
