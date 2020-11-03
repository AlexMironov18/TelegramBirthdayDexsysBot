package com.dexsys.TelegramBotDexsys.services;

import com.dexsys.TelegramBotDexsys.domain.services.entities.User;

import java.util.List;

public interface IRepository {

    void addUser(User user);
    List<User> getUserList();
    User getUser(String phoneNumber);
    boolean deleteUser(String phoneNumber);

}
