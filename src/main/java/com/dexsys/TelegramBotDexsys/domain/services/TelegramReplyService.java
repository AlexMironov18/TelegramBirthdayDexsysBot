package com.dexsys.TelegramBotDexsys.domain.services;

import com.dexsys.TelegramBotDexsys.app.clientService.telegramHandlers.DTO.UserDTO;
import com.dexsys.TelegramBotDexsys.services.IRepository;
import com.dexsys.TelegramBotDexsys.domain.repositories.UserRepository;
import com.dexsys.TelegramBotDexsys.services.ITelegramReplyService;
import com.dexsys.TelegramBotDexsys.services.ITelegramService;
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

    @Autowired
    private ITelegramService telegramService;

    //creating reply message
    @Override
    public synchronized SendMessage sendMsg(UserDTO userDTO) throws TelegramApiException {
        //creating blank message to be send
        SendMessage outputMessage = new SendMessage();
        //filling the output message with destination(chatId)
        outputMessage.setChatId(userDTO.getChatId());
        //sending text and keyboard if user is authorized
        if (((UserRepository) userRepository).getChatIdMap().get(userDTO.getChatId()) != null) {
            setText(outputMessage, userDTO);
            setButtons(outputMessage);
            return outputMessage;
            //sending text and keyboard if user isn't authorized
        } else {
            setTextDefault(outputMessage, userDTO);
            setButtonsDefault(outputMessage);
            return outputMessage;
        }
    }


    @Override
    public synchronized void setText(SendMessage sendMessage, UserDTO userDTO) {
        switch(userDTO.getText()) {
            case "Показать профиль": sendMessage.setText(telegramService.getUser(userDTO.getChatId()).toString());
                break;
            case "Показать пользователей": sendMessage.setText(telegramService.getUsers().toString());
                break;
            case "INFO": sendMessage.setText("Кнопка \"Введите вашу дату рождения\" позволяет " +
                    "ввести дату рождения данного пользователя в систему\nКнопка \"Показать пользователей\" " +
                    "показывает данные всех пользователей этой системы");
                break;
            case "Ввести номер телефона": sendMessage.setText("Вы авторизованы в системе");
                break;
            case "Пользователь не найден":
                sendMessage.setText("Пользователя с телефоном " + userDTO.getPhone() + " нет в базе данных");
                break;
            default: sendMessage.setText(userDTO.getText());
        }
    }
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
        keyboardFirstRow.add(new KeyboardButton("Показать профиль"));
        keyboardFirstRow.add(new KeyboardButton("Очистить профиль"));
        keyboardSecondRow.add(new KeyboardButton("Показать пользователей"));
        keyboardSecondRow.add(new KeyboardButton("INFO"));
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
    }


    @Override
    public synchronized void setTextDefault(SendMessage sendMessage, UserDTO userDTO) {
        switch(userDTO.getText()) {
            case "INFO": sendMessage.setText("Для авторизации в системе и получения возможностей " +
                    "к основным функциям бота - нажмите \"Ввести номер телефона\".\n" +
                    "До авторизации, бот будет присылать вам ответ в виде введеных вами сообщений. ");
                break;
            case "Очистить профиль": sendMessage.setText("Ваш профиль очищен, вы не авторизованы в системе");
                break;
            default: sendMessage.setText(userDTO.getText());
        }
    }
    @Override
    public synchronized void setButtonsDefault(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        KeyboardButton contactKeyButton = new KeyboardButton();
        contactKeyButton.setText("Ввести номер телефона").setRequestContact(true);
        keyboardFirstRow.add(contactKeyButton);
        keyboardSecondRow.add(new KeyboardButton("INFO"));
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
    }
}
