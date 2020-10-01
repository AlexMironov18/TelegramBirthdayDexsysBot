package com.dexsys.TelegramBotDexsys;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Map;

@AllArgsConstructor
@Component
@Data
public class UserRepository {
    private Map<String, User> userMap;

    public void addUser(User user) {
        userMap.put(user.getUserName(), user);
    }
}
