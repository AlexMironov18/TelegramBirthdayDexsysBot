package com.dexsys.TelegramBotDexsys.clientServices;

import com.dexsys.TelegramBotDexsys.clientServices.DTO.UserDTO;
import com.dexsys.TelegramBotDexsys.repositories.IRepository;
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

    @Override
    public void onUpdateReceived(Update update) {
        UserDTO userDTO = UserDTO.fromUpdate(update);
        //adding user who did the update to repository (if he's not in the rep yet)
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                telegramService.processMessage(userDTO);
                execute(telegramService.sendMsg(userDTO.getChatId(), userDTO.getText()));
                System.out.println(userRepository.printUserList());
            }

        } catch (TelegramApiException e) {
            log.error("RepeaterHandler: " + e.toString());
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




//    @Override
//    public void onUpdateReceived(Update update) {
//
//        //adding user who did the update to repository (if he's not in the rep yet)
//        userRepository.addUserToRepository(update);
//        try {
//            if (update.hasMessage() && update.getMessage().hasText()) {
//                //creating message from update(i.e. request from user)
//                Message inputMessage = update.getMessage();
//
//                    //handling setting a birthdate
//                    if (toSetBDate) {
//                        telegramService.setBDay(inputMessage.getChatId(), inputMessage.getFrom().getUserName(), inputMessage.getText());
//                        toSetBDate = false;
//                    } else if (inputMessage.getText().equals("Ввести дату рождения")) {
//                        toSetBDate = true;
//                    }
//
//                    //default behavior
//                    SendMessage outputMessage = telegramService.sendMsg(inputMessage.getChatId(), inputMessage.getText());
//                    telegramService.setButtons(outputMessage);
//                    execute(outputMessage);
//                    System.out.println(userRepository.printUserList());
//                }
//        } catch (TelegramApiException e) {
//            log.error("RepeaterHandler: " + e.toString());
//        }
//    }
//
//    @Override
//    public final String getBotUsername() {
//        return "BDayDexsysBot";
//    }
//
//    @Override
//    public final String getBotToken() {
//        return "1086644086:AAFKbh2qwM6oEl0-bCCxAITus0VfvhiU9SY";
//    }
}
