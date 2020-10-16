package com.dexsys.TelegramBotDexsys.services.implementation;

import com.dexsys.TelegramBotDexsys.clientServices.RepeaterHandler;
import com.dexsys.TelegramBotDexsys.clientServices.DTO.UserDTO;
import com.dexsys.TelegramBotDexsys.repositories.UserRepository;
import com.dexsys.TelegramBotDexsys.repositories.IRepository;
import com.dexsys.TelegramBotDexsys.services.ITelegramService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;


import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class TelegramService implements ITelegramService {

    @Autowired
    private IRepository userRepository;

    @Autowired
    private RepeaterHandler handler;

    private boolean toSetBDate = false;

    @Override
    @PostConstruct
    public void setupBot() {
        TelegramBotsApi botsApi = new TelegramBotsApi();
        try {
            botsApi.registerBot(handler);
        } catch (TelegramApiException e) {
            log.error("setupBot: " + e.toString());
        }
    }

    public void processMessage(UserDTO userDTO) throws TelegramApiException {
        userRepository.createAndAddUserToRepository(userDTO);
        if (toSetBDate) {
            setBDay(userDTO);
            toSetBDate = false;
            log.info("Установлена дата рождения \"{}\" для пользователя {}", userDTO.getText(), userDTO.getUserName());
        } else if (userDTO.getText().equals("Ввести дату рождения")) {
            toSetBDate = true;
        }
    }

    //setting birthdate of the user
    @Override
    public synchronized void setBDay(UserDTO userDTO) throws TelegramApiException {
        ((UserRepository) userRepository).getUserMap().get(userDTO.getUserName()).setBDate(userDTO.getText());
    }

    @Override
    public synchronized SendMessage sendMsg(UserDTO userDTO) throws TelegramApiException {
        //creating blank message to be send
        SendMessage outputMessage = new SendMessage();
        //filling the outout message with destination(chatId) and content(text)
        outputMessage.setChatId(userDTO.getChatId());
        outputMessage.setText(userDTO.getText());
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
        keyboardFirstRow.add(new KeyboardButton("Ввести дату рождения"));
        keyboard.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
    }
}
