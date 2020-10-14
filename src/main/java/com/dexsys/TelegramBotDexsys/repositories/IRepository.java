package com.dexsys.TelegramBotDexsys.repositories;

import com.dexsys.TelegramBotDexsys.services.entities.User;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface IRepository {

    String printUserList();
    void addUser(User user);
    void addUserToRepository(Update update);

}
