package com.dexsys.TelegramBotDexsys.repositories;

import com.dexsys.TelegramBotDexsys.clientServices.DTO.UserDTO;
import com.dexsys.TelegramBotDexsys.services.entities.User;

public interface IRepository {

    String printUserList();
    void addUser(User user);
    void createAndAddUserToRepository(UserDTO userDTO);

}
