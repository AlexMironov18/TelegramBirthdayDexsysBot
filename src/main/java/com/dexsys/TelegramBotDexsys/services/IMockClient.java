package com.dexsys.TelegramBotDexsys.services;

import com.dexsys.TelegramBotDexsys.app.clientService.mockClient.mockDTO.UserMockDTO;

import java.util.List;
import java.util.UUID;

public interface IMockClient {
    //get generate delete getAll
    UserMockDTO getUser(UUID uuid);
    List<UserMockDTO> getAll();
    UserMockDTO createUser(UserMockDTO userMockDTO);
    UserMockDTO generateUser();
}
