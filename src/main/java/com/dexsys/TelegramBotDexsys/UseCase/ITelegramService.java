package com.dexsys.TelegramBotDexsys.UseCase;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface ITelegramService {

    void setup();
    void setBDay(long chatId, String userName, String text) throws TelegramApiException;
    SendMessage sendMsg(long chatId, String text) throws TelegramApiException;

}
