package com.dexsys.TelegramBotDexsys.services;

import com.dexsys.TelegramBotDexsys.clientServices.DTO.UserDTO;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface ITelegramService {

    void setupBot();
    void setBDay(UserDTO userDTO) throws TelegramApiException;
    SendMessage sendMsg(UserDTO userDTO) throws TelegramApiException;
    void setButtons(SendMessage sendMessage);
    void processMessage(UserDTO userDTO) throws TelegramApiException;

}
