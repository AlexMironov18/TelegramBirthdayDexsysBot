package com.dexsys.TelegramBotDexsys.repositories;

import com.dexsys.TelegramBotDexsys.services.entities.User;

import java.util.List;

public interface IRepository {

    String printUsers();
    String printUserList();
    void addUser(User user);
    void createAndAddUserToRepository(User user);
    List<User> getUserList();

}
