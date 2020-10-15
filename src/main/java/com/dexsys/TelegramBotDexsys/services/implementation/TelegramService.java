package com.dexsys.TelegramBotDexsys.services.implementation;

import com.dexsys.TelegramBotDexsys.clientServices.RepeaterHandler;
import com.dexsys.TelegramBotDexsys.clientServices.DTO.UserDTO;
import com.dexsys.TelegramBotDexsys.repositories.UserRepository;
import com.dexsys.TelegramBotDexsys.repositories.IRepository;
import com.dexsys.TelegramBotDexsys.services.ITelegramService;
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
public class TelegramService implements ITelegramService {

    @Autowired
    private IRepository userRepository;

    @Autowired
    private RepeaterHandler handler;

    private boolean toSetBDate = false;

    public void processMessage(UserDTO userDTO) throws TelegramApiException {
        userRepository.addUserToRepository(userDTO);
        if (toSetBDate) {
            setBDay(userDTO.getChatId(), userDTO.getUserName(), userDTO.getText());
            toSetBDate = false;
        } else if (userDTO.getText().equals("Ввести дату рождения")) {
            toSetBDate = true;
        }
    }

    @Override
    @PostConstruct
    public void setup() {
        TelegramBotsApi botsApi = new TelegramBotsApi();
        try {
            botsApi.registerBot(handler);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    //setting birthdate of the user
    @Override
    public synchronized void setBDay(long chatId, String userName, String text) throws TelegramApiException {
        ((UserRepository) userRepository).getUserMap().get(userName).setBDate(text);
    }

    @Override
    public synchronized SendMessage sendMsg(long chatId, String text) throws TelegramApiException {
        //creating blank message to be send
        SendMessage outputMessage = new SendMessage();
        //filling the outout message with destination(chatId) and content(text)
        outputMessage.setChatId(chatId);
        outputMessage.setText(text);
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
