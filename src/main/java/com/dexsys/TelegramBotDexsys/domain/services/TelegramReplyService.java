package com.dexsys.TelegramBotDexsys.domain.services;

import com.dexsys.TelegramBotDexsys.app.clientService.telegramHandlers.DTO.UserDTO;
import com.dexsys.TelegramBotDexsys.domain.services.entities.User;
import com.dexsys.TelegramBotDexsys.services.*;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class TelegramReplyService implements ITelegramReplyService {

    @Autowired
    private IDataService dataService;

    //creating reply message
    @Override
    public synchronized SendMessage sendMsg(UserDTO userDTO) throws TelegramApiException {
        SendMessage outputMessage = new SendMessage();
        outputMessage.setChatId(userDTO.getChatId());
        //sending text and keyboard if user is authorized
        if (dataService.isUserExist(userDTO.getChatId())) {
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
            case "Показать профиль":
                User userToShow = dataService.getUser(userDTO.getChatId());

                    sendMessage.setText(
                        "Name: " +
                                (userToShow.getFirstName() == null ? "неизвестно" : userToShow.getFirstName())+"\n"+
                        "SecondName: " +
                                (userToShow.getSecondName() == null ? "неизвестно" : userToShow.getSecondName())+"\n"+
                        "MiddleName: " +
                                (userToShow.getMiddleName() == null ? "неизвестно" : userToShow.getMiddleName())+"\n"+
                        "Gender: " +
                                (userToShow.isMale() ? "Male" : "Female")+"\n"+
                        "BirthDate: " +
                                (userToShow.getBirthDate() == null ? "неизвестно" : userToShow.getBirthDate())+"\n"+
                        "Phone: " +
                                (userToShow.getPhone() == null ? "неизвестно" : userToShow.getPhone())+"\n"+
                        "ID: " +
                                (userToShow.getId() == null ? "неизвестно" : userToShow.getId())+"\n"+
                        "ChatID: " +
                                (userToShow.getChatId() == null ? "неизвестно" : userToShow.getChatId()));
                        log.info("Отправлено сообщение о профиле пользователя в Telegram-чат с chatId: {}",
                                userDTO.getChatId());
                break;
            case "Показать пользователей": sendMessage.setText(dataService.getUsers().toString());
                log.info("Отправлено сообщение о профилях всех пользователей в Telegram-чат с chatId: {}",
                        userDTO.getChatId());
                break;
            case "Информация": sendMessage.setText("Кнопка \"Изменить дату рождения\" позволяет " +
                    "изменить дату рождения в вашем профиле\n" +
                    "Кнопка \"Показать пользователей\" " +
                    "показывает информацию о всех пользователях этого приложения\n" +
                    "Кнопка \"Показать профиль\" показывает информацию вашего профиля\n" +
                    "Кнопка \"Очистить профиль\" позволяет вам выйти из приложения\n" +
                    "Для обновления информации с основной базы данных - выполните \"Очистить профиль\"" +
                    " и затем, \"Авторизоваться по номеру телефона\"");
                log.info("Отправлено сообщение об описании интерфейса в Telegram-чат с chatId: {}",
                        userDTO.getChatId());
                break;
            case "Авторизоваться по номеру телефона": sendMessage.setText("Вы авторизованы в системе");
                log.info("Отправлено сообщение об успешной авторизации пользователя в Telegram-чат с chatId: {}",
                        userDTO.getChatId());
                break;
            case "Пользователь не найден":
                sendMessage.setText("Пользователя с телефоном " + userDTO.getPhone() + " нет в базе данных");
                log.info("Отправлено сообщение \"пользователь не найден\" в Telegram-чат с chatId: {}",
                        userDTO.getChatId());
                break;
            default:
                sendMessage.setText(userDTO.getText());
                log.info("Отправлено сообщение \"{}\" в Telegram-чат с chatId: {}",
                        userDTO.getText(), userDTO.getChatId());
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
        KeyboardRow keyboardThirdRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("Показать профиль"));
        keyboardFirstRow.add(new KeyboardButton("Очистить профиль"));
        keyboardSecondRow.add(new KeyboardButton("Показать пользователей"));
        keyboardSecondRow.add(new KeyboardButton("Информация"));
        keyboardThirdRow.add(new KeyboardButton("Изменить дату рождения"));
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        keyboard.add(keyboardThirdRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
    }


    @Override
    public synchronized void setTextDefault(SendMessage sendMessage, UserDTO userDTO) {
        switch(userDTO.getText()) {
            case "Информация": sendMessage.setText("Для авторизации в системе и получения возможностей " +
                    "к основным функциям бота - нажмите \"Авторизоваться по номеру телефона\".\n" +
                    "До авторизации, бот будет присылать вам ответ в виде введеных вами сообщений. ");
                log.info("Отправлено сообщение об описании интерфейса в Telegram-чат с chatId: {}",
                        userDTO.getChatId());
                break;
            case "Очистить профиль": sendMessage.setText("Ваш профиль очищен, вы не авторизованы в системе");
                log.info("Отправлено сообщение об очистке профиля пользователя в Telegram-чат с chatId: {}",
                        userDTO.getChatId());
                break;
            default:
                sendMessage.setText(userDTO.getText());
                log.info("Отправлено сообщение \"{}\" в Telegram-чат с chatId: {}",
                        userDTO.getText(), userDTO.getChatId());
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
        contactKeyButton.setText("Авторизоваться по номеру телефона").setRequestContact(true);
        keyboardFirstRow.add(contactKeyButton);
        keyboardSecondRow.add(new KeyboardButton("Информация"));
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
    }
}
