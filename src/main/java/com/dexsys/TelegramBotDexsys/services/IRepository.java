package com.dexsys.TelegramBotDexsys.services;

import com.dexsys.TelegramBotDexsys.app.web.webDTO.UserWebDTO;
import com.dexsys.TelegramBotDexsys.domain.repositories.DTO.UserDbDTO;

import java.util.List;

public interface IRepository {

    List<UserDbDTO> getUserList();
    UserDbDTO getUser(String phoneNumber);
    Integer addUser(UserWebDTO userWebDTO);
    Integer deleteUser(String phoneNumber);

}
