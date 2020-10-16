package com.dexsys.TelegramBotDexsys.clientServices.DTO;

import lombok.Builder;
import lombok.Data;
import org.telegram.telegrambots.meta.api.objects.Update;

@Builder
@Data
public class UserDTO {

    private long chatId;
    private String userName;
    private String text;

    @Builder
    public static UserDTO fromUpdate(Update update) {
        return UserDTO.builder()
                .chatId(update.getMessage().getChatId())
                .userName(update.getMessage().getFrom().getUserName())
                .text(update.getMessage().getText())
                .build();
    }
}
