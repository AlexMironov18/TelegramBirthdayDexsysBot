package com.dexsys.TelegramBotDexsys.services;

import com.dexsys.TelegramBotDexsys.clientServices.DTO.UserDTO;
import com.dexsys.TelegramBotDexsys.services.entities.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface ITelegramService {

    void setupBot();
    void setBirthDay(User user, String birthDate) throws TelegramApiException;
    void processMessage(UserDTO userDTO) throws TelegramApiException;

}
