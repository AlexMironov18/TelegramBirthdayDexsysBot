package com.dexsys.TelegramBotDexsys.UseCase;

import com.dexsys.TelegramBotDexsys.Controllers.RepeaterHandler;
import com.dexsys.TelegramBotDexsys.Gateways.UserRepository;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;

import static com.dexsys.TelegramBotDexsys.TelegramBotDexsysApplication.ctx;

import java.util.ArrayList;
import java.util.List;


@Service
public class TelegramService implements ITelegramService {

    @Override
    @PostConstruct
    public void setup() {
        TelegramBotsApi botsApi = new TelegramBotsApi();
        try {
            botsApi.registerBot(new RepeaterHandler());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    //setting birthdate of the user
    @Override
    public synchronized void setBDay(long chatId, String userName, String text) throws TelegramApiException {
        IRepository userRepository = (UserRepository) ctx.getBean("userRepository");
        ((UserRepository) userRepository).getUserMap().get(userName).setBDate(text);
    }

    @Override
    public synchronized SendMessage sendMsg(long chatId, String text) throws TelegramApiException {
        //creating blank message to be send
        SendMessage outputMessage = new SendMessage();
        //filling the outout message with destination(chatId) and content(text)
        outputMessage.setChatId(chatId);
        outputMessage.setText(text);
        return outputMessage;
    }

    //adding keyboard
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
