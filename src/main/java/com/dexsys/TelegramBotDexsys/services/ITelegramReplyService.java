package com.dexsys.TelegramBotDexsys.services;

import com.dexsys.TelegramBotDexsys.clientServices.DTO.UserDTO;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface ITelegramReplyService {

    SendMessage sendMsg(UserDTO userDTO) throws TelegramApiException;
    void setButtons(SendMessage sendMessage);

}
