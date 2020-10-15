package com.dexsys.TelegramBotDexsys.services;

import com.dexsys.TelegramBotDexsys.clientServices.DTO.UserDTO;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface ITelegramService {

    void setup();
    void setBDay(long chatId, String userName, String text) throws TelegramApiException;
    SendMessage sendMsg(long chatId, String text) throws TelegramApiException;
    void setButtons(SendMessage sendMessage);
    void processMessage(UserDTO userDTO) throws TelegramApiException;

}
