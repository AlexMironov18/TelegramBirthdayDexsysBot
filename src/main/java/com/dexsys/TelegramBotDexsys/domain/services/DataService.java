package com.dexsys.TelegramBotDexsys.domain.services;

import com.dexsys.TelegramBotDexsys.domain.services.entities.User;
import com.dexsys.TelegramBotDexsys.services.IDataService;
import com.dexsys.TelegramBotDexsys.services.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataService implements IDataService {

    @Autowired
    private IUserRepository repository;

    @Override
    public void addUser(User user) {
        repository.save(user);
    }

    @Override
    public void updateUser(User user) {
        User userToUpdate = getUser(user.getChatId());
        userToUpdate.setBirthDate(user.getBirthDate());
        addUser(userToUpdate);
    }

    @Override
    public List<User> getUsers() {
        return (List<User>) repository.findAll();
    }

    @Override
    public User getUser(String chatId) {
        if (isUserExist(chatId)) {
            return repository.findById(chatId).get();
        } else return null;
    }

    @Override
    public boolean deleteUser(String chatId) {
        if (isUserExist(chatId)) {
            repository.deleteById(chatId);
            return true;
        } else return false;
    }

    @Override
    public boolean isUserExist(String chatId) {
        return repository.existsById(chatId);
    }
}
