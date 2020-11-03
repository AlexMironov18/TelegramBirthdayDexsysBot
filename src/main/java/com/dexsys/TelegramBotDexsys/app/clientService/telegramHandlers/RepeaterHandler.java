package com.dexsys.TelegramBotDexsys.app.clientService.telegramHandlers;

import com.dexsys.TelegramBotDexsys.app.clientService.mockClient.MockClient;
import com.dexsys.TelegramBotDexsys.app.clientService.telegramHandlers.DTO.UserDTO;
import com.dexsys.TelegramBotDexsys.services.IRepeaterHandler;
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
public class RepeaterHandler extends TelegramLongPollingBot implements IRepeaterHandler {

    @Autowired
    private ITelegramService telegramService;
    @Autowired
    private ITelegramReplyService telegramReplyService;

    @Override
    public void onUpdateReceived(Update update) {
        UserDTO userDTO;
        try {
            //when authorizing
            if (update.getMessage().getContact() != null) {
                userDTO = UserDTO.createRegisterUserDTO(update);
                userDTO.setText("Ввести номер телефона");
                execute(telegramService.processAuthorizationMessage(userDTO));
                //default behavior
            } else if (update.hasMessage() && update.getMessage().hasText()) {
                userDTO = UserDTO.createUserDTO(update);
                execute(telegramService.processMessage(userDTO));
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
