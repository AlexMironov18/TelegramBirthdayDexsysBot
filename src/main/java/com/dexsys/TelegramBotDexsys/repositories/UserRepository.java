package com.dexsys.TelegramBotDexsys.repositories;

import com.dexsys.TelegramBotDexsys.services.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Repository;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

@AllArgsConstructor
@Repository
@Data
public class UserRepository implements IRepository {

    private Map<String, User> userMap;

    @Override
    public String printUserList() {
        return userMap.toString();
    }

    @Override
    public void addUser(User user) {
        userMap.put(user.getUserName(), user);
    }

    @Override
    public void addUserToRepository(Update update) {
        if (!userMap.containsKey(update.getMessage().getFrom().getUserName())) {
            addUser(User.fromUpdate(update));
        }
    }
}
