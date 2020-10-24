package com.dexsys.TelegramBotDexsys.services.implementation;

import com.dexsys.TelegramBotDexsys.handlers.DTO.UserDTO;
import com.dexsys.TelegramBotDexsys.repositories.IRepository;
import com.dexsys.TelegramBotDexsys.services.ITelegramReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Service
public class TelegramReplyService implements ITelegramReplyService {

    @Autowired
    private IRepository userRepository;

    //creating reply message
    @Override
    public synchronized SendMessage sendMsg(UserDTO userDTO) throws TelegramApiException {
        //creating blank message to be send
        SendMessage outputMessage = new SendMessage();
        //filling the output message with destination(chatId) and content(text)
        outputMessage.setChatId(userDTO.getChatId());
        switch(userDTO.getText()) {
            case "Ввести дату рождения": outputMessage.setText("Введите вашу дату рождения:");
                break;
            case "Показать пользователей": outputMessage.setText(userRepository.printUsers());
                break;
            case "INFO": outputMessage.setText("Кнопка \"Введите вашу дату рождения\" позволяет " +
                    "ввести дату рождения данного пользователя в систему\nКнопка \"Показать пользователей\" " +
                    "показывает данные всех пользователей этой системы");
                break;
            default: outputMessage.setText(userDTO.getText());
        }
        setButtons(outputMessage);
        return outputMessage;
    }

    //adding keyboard
    @Override
    public synchronized void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("Ввести дату рождения"));
        keyboardSecondRow.add(new KeyboardButton("Показать пользователей"));
        keyboardSecondRow.add(new KeyboardButton("INFO"));
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
    }
}
