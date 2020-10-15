package com.dexsys.TelegramBotDexsys.repositories;

import com.dexsys.TelegramBotDexsys.clientServices.DTO.UserDTO;
import com.dexsys.TelegramBotDexsys.services.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Repository;

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
    public void addUserToRepository(UserDTO userDTO) {
        if (!userMap.containsKey(userDTO.getUserName())) {
            addUser(User.createUser(userDTO));
        }
    }
}
