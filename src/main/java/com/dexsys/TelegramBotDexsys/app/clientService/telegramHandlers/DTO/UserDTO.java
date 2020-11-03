package com.dexsys.TelegramBotDexsys.app.clientService.telegramHandlers.DTO;

import lombok.Builder;
import lombok.Data;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Date;
import java.util.UUID;

@Builder
@Data
public class UserDTO {

    private String chatId;
    private String text;
    private String phone;

    @Builder
    public static UserDTO createUserDTO(Update update) {
        return UserDTO.builder()
                .chatId(update.getMessage().getChatId().toString())
                .text(update.getMessage().getText())
                .build();
    }

    @Builder
    public static UserDTO createRegisterUserDTO(Update update) {
        return UserDTO.builder()
                .chatId(update.getMessage().getChatId().toString())
                .phone(update.getMessage().getContact().getPhoneNumber())
                .build();
    }
}
