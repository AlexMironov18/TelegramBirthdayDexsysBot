package com.dexsys.TelegramBotDexsys.UseCase;

import com.dexsys.TelegramBotDexsys.Entities.User;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface IRepository {

    String printUserList();
    void addUser(User user);
    void addUserToRepository(Update update);

}
