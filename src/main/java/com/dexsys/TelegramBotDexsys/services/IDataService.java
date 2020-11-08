package com.dexsys.TelegramBotDexsys.services;

import com.dexsys.TelegramBotDexsys.domain.services.entities.User;

import java.util.List;

public interface IDataService {

    void addUser(User user);
    void updateUser(User user);
    List<User> getUsers();
    User getUser(String chatId);
    boolean deleteUser(String chatId);
    boolean isUserExist(String chatId);
}
