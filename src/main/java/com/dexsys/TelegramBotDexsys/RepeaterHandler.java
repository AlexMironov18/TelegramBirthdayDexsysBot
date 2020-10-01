package com.dexsys.TelegramBotDexsys;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class RepeaterHandler extends TelegramLongPollingBot {

    ApplicationContext ctx = new AnnotationConfigApplicationContext(TelegramBotDexsysApplication.class);


    private boolean toSetBDate = false;
    @Override
    public void onUpdateReceived(Update update) {
        //adding user who did the update to repository
        addUserToRepository(update);
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                //creating message from update(i.e. request from user)
                Message inputMessage = update.getMessage();
                    if (toSetBDate) {
                        setBDay(inputMessage.getChatId(), inputMessage.getFrom().getUserName(), inputMessage.getText());
                        toSetBDate = false;
                    } else if (inputMessage.getText().equals("Ввести дату рождения")) {
                        toSetBDate = true;
                    }

                    sendMsg(inputMessage.getChatId(), inputMessage.getText());
                    System.out.println(printUserList());

                }
        } catch (TelegramApiException e) {
            log.error("RepeaterHandler: " + e.toString());
        }
    }


    public synchronized void setButtons(SendMessage sendMessage) {
        // Создаем клавиуатуру
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

    //крч тут создается новый репозиторий с мапой и пользователем
    public synchronized void setBDay(long chatId, String userName, String text) throws TelegramApiException {
        UserRepository userRepository = (UserRepository) ctx.getBean("userRepository");
        userRepository.getUserMap().get(userName).setBDate(text);
    }
    public synchronized void sendMsg(long chatId, String text) throws TelegramApiException {
        //creating blank message to be send
        SendMessage outputMessage = new SendMessage();
        //filling the outout message with destination(chatId) and content(text)
        outputMessage.setChatId(chatId);
        outputMessage.setText(text);

        setButtons(outputMessage);
        //sending output message
        execute(outputMessage);
    }

    public String printUserList() {
        UserRepository userRepository = (UserRepository) ctx.getBean("userRepository");
        return userRepository.getUserMap().toString();
    }

    public void addUserToRepository(Update update) {
        UserRepository userRepository = (UserRepository) ctx.getBean("userRepository");
        if (!userRepository.getUserMap().containsKey(update.getMessage().getFrom().getUserName())) {
            userRepository.addUser(User.fromUpdate(update));
        }
    }

    @Override
    public String getBotUsername() {
        return "BDayDexsysBot";
    }

    @Override
    public String getBotToken() {
        return "1086644086:AAFKbh2qwM6oEl0-bCCxAITus0VfvhiU9SY";
    }
}
