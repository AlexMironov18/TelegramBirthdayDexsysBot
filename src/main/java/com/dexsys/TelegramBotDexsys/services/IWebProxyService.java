package com.dexsys.TelegramBotDexsys.services;

import com.dexsys.TelegramBotDexsys.app.clientService.mockClient.mockDTO.UserMockDTO;
import com.dexsys.TelegramBotDexsys.app.web.webDTO.UserWebDTO;

import java.util.List;
import java.util.UUID;

public interface IWebProxyService {

    UserWebDTO getUser(UUID uuid);
    List<UserWebDTO> getUsers();
    UserMockDTO createUser(UserWebDTO userWebDTO);
    UserMockDTO generateUser();

}
