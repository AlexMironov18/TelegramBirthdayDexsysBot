package com.dexsys.TelegramBotDexsys.services.entities;

import lombok.Builder;
import lombok.Data;
import org.telegram.telegrambots.meta.api.objects.Update;

@Builder
@Data
public class User {

    private long chatId;
    private String userName;
    private String bDate;

    @Builder
    public static User fromUpdate(Update update) {
        return User.builder().chatId(update.getMessage().getChatId())
                .userName(update.getMessage().getFrom().getUserName())
                .build();
    }
}
