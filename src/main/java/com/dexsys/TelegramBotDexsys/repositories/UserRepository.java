package com.dexsys.TelegramBotDexsys.repositories;

import com.dexsys.TelegramBotDexsys.services.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Repository
@Slf4j
@Data
public class UserRepository implements IRepository {

    private Map<String, User> userMap;

    @Override
    public String printUsers() {
        return userMap.toString();
    }
    @Override
    public String printUserList() {
        return userMap.toString();
    }

    @Override
    public void addUser(User user) {
        userMap.put(user.getUserName(), user);
        log.info("Добавлен пользователь в базу данных с именем {}", user.getUserName());
    }

    @Override
    public void createAndAddUserToRepository(User user) {
        if (!userMap.containsKey(user.getUserName())) {
            addUser(user);
        }
    }

    @Override
    public List<User> getUserList() {
        return userMap.values().stream().collect(Collectors.toList());
    }

    @Override
    public User getUser(String userName) {
        return userMap.get(userName);
    }
}
