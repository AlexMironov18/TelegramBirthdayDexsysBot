package com.dexsys.TelegramBotDexsys.services;

import com.dexsys.TelegramBotDexsys.app.web.webDTO.UserWebDTO;
import com.dexsys.TelegramBotDexsys.domain.services.entities.User;

import java.util.List;

public interface IRepository {

    List<UserWebDTO> getUserList();
    UserWebDTO getUser(String phoneNumber);
    Integer addUser(UserWebDTO userWebDTO);
    Integer deleteUser(String phoneNumber);

}
