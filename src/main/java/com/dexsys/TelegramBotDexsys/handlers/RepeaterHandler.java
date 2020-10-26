package com.dexsys.TelegramBotDexsys.handlers;

import com.dexsys.TelegramBotDexsys.handlers.DTO.UserDTO;
import com.dexsys.TelegramBotDexsys.repositories.IRepository;
import com.dexsys.TelegramBotDexsys.services.ITelegramReplyService;
import com.dexsys.TelegramBotDexsys.services.ITelegramService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Slf4j
public class RepeaterHandler extends TelegramLongPollingBot {

    @Autowired
    private IRepository userRepository;
    @Autowired
    private ITelegramService telegramService;
    @Autowired
    private ITelegramReplyService telegramReplyService;

    @Override
    public void onUpdateReceived(Update update) {
        UserDTO userDTO;
        try {
            //if "enter phone number" is pressed
            if (update.getMessage().getContact() != null) {
                userDTO = UserDTO.createRegisterUserDTO(update);
                userDTO.setText("Ввести номер телефона");
                telegramService.processMessage(userDTO);
                execute(telegramReplyService.sendMsg(userDTO));
            } else if (update.hasMessage() && update.getMessage().hasText()) {
                userDTO = UserDTO.createUserDTO(update);
                telegramService.processMessage(userDTO);
                execute(telegramReplyService.sendMsg(userDTO));
                log.info("Отправлено сообщение \"{}\" в чат {}", userDTO.getText(), userDTO.getChatId());
            }
        } catch (TelegramApiException e) {
            log.error("onUpdateReceived: " + e.toString());
        }
    }

    @Override
    public final String getBotUsername() {
        return "BDayDexsysBot";
    }

    @Override
    public final String getBotToken() {
        return "1086644086:AAFKbh2qwM6oEl0-bCCxAITus0VfvhiU9SY";
    }

}
