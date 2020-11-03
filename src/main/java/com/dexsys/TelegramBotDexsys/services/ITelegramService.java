package com.dexsys.TelegramBotDexsys.services;

import com.dexsys.TelegramBotDexsys.app.clientService.telegramHandlers.DTO.UserDTO;
import com.dexsys.TelegramBotDexsys.domain.services.entities.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public interface ITelegramService {

    void setupBot();
    //void setBirthDay(User user, String birthDate) throws TelegramApiException;
    SendMessage processMessage(UserDTO userDTO) throws TelegramApiException;
    SendMessage processAuthorizationMessage(UserDTO userDTO) throws TelegramApiException;
    List<User> getUsers();
    User getUser(String chatId);
    boolean deleteUser(String chatId);

}
