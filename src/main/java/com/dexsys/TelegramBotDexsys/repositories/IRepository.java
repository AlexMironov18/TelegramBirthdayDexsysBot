package com.dexsys.TelegramBotDexsys.repositories;

import com.dexsys.TelegramBotDexsys.clientServices.DTO.UserDTO;
import com.dexsys.TelegramBotDexsys.services.entities.User;

public interface IRepository {

    String printUsers();
    String printUserList();
    void addUser(User user);
    void createAndAddUserToRepository(User user);

}
