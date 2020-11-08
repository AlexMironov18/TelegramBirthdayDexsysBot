package com.dexsys.TelegramBotDexsys.services;

import com.dexsys.TelegramBotDexsys.app.clientService.mockClient.mockDTO.UserMockDTO;
import com.dexsys.TelegramBotDexsys.app.web.webDTO.UserWebDTO;
import org.springframework.http.HttpMethod;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface IWebProxyService {

    UserWebDTO getUser(UUID uuid);
    List<UserWebDTO> getUsers();
    String createUser(UserWebDTO userWebDTO);
    UserMockDTO generateUser();
    Set<HttpMethod> getOptions(UUID uuid);

}
