package com.dexsys.TelegramBotDexsys.Controllers;

import com.dexsys.TelegramBotDexsys.UseCase.TelegramService;
import com.dexsys.TelegramBotDexsys.Gateways.UserRepository;
import com.dexsys.TelegramBotDexsys.UseCase.ITelegramService;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import static com.dexsys.TelegramBotDexsys.TelegramBotDexsysApplication.ctx;


@Slf4j
public class RepeaterHandler extends TelegramLongPollingBot {

    //if true, means the command to set a BirthDate was sent
    private boolean toSetBDate = false;
    @Override
    public void onUpdateReceived(Update update) {
        //adding user who did the update to repository (if he's not in the rep yet)
        ctx.getBean(UserRepository.class).addUserToRepository(update);
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                //creating message from update(i.e. request from user)
                Message inputMessage = update.getMessage();
                    //handling setting a birthdate
                    if (toSetBDate) {
                        ctx.getBean(TelegramService.class).setBDay(inputMessage.getChatId(), inputMessage.getFrom().getUserName(), inputMessage.getText());
                        toSetBDate = false;
                    } else if (inputMessage.getText().equals("Ввести дату рождения")) {
                        toSetBDate = true;
                    }
                    //default behavior
                    ITelegramService action = ctx.getBean(TelegramService.class);
                    SendMessage outputMessage = action.sendMsg(inputMessage.getChatId(), inputMessage.getText());
                    ((TelegramService) action).setButtons(outputMessage);
                    execute(outputMessage);
                    System.out.println(ctx.getBean(UserRepository.class).printUserList());
                }
        } catch (TelegramApiException e) {
            log.error("RepeaterHandler: " + e.toString());
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
