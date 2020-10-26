package com.dexsys.TelegramBotDexsys.services;

import com.dexsys.TelegramBotDexsys.handlers.DTO.UserDTO;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface ITelegramReplyService {

    SendMessage sendMsg(UserDTO userDTO) throws TelegramApiException;
    void setText(SendMessage sendMessage, UserDTO userDTO);
    void setButtons(SendMessage sendMessage);
    void setTextDefault(SendMessage sendMessage, UserDTO userDTO);
    void setButtonsDefault(SendMessage sendMessage);

}
