package com.dexsys.TelegramBotDexsys.Gateways;

import com.dexsys.TelegramBotDexsys.Entities.User;
import com.dexsys.TelegramBotDexsys.UseCase.IRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Repository;
import org.telegram.telegrambots.meta.api.objects.Update;
import static com.dexsys.TelegramBotDexsys.TelegramBotDexsysApplication.ctx;

import java.util.Map;

@AllArgsConstructor
@Repository
@Data
public class UserRepository implements IRepository {

    private Map<String, User> userMap;

    @Override
    public String printUserList() {
        IRepository userRepository = (UserRepository) ctx.getBean("userRepository");
        return ((UserRepository) userRepository).getUserMap().toString();
    }

    @Override
    public void addUser(User user) {
        userMap.put(user.getUserName(), user);
    }

    @Override
    public void addUserToRepository(Update update) {
        UserRepository userRepository = (UserRepository) ctx.getBean("userRepository");
        if (!userRepository.getUserMap().containsKey(update.getMessage().getFrom().getUserName())) {
            userRepository.addUser(User.fromUpdate(update));
        }
    }
}
