package com.dexsys.TelegramBotDexsys.handlers.DTO;

import lombok.Builder;
import lombok.Data;
import org.telegram.telegrambots.meta.api.objects.Update;

@Builder
@Data
public class UserDTO {

    private long chatId;
    private String userName;
    private String text;
    private long id;
    private boolean isMale;
    private String firstName;
    private String lastName;
    private String middleName;
    private String phone;

    @Builder
    public static UserDTO createUserDTO(Update update) {
        return UserDTO.builder()
                .chatId(update.getMessage().getChatId())
                .firstName(update.getMessage().getFrom().getFirstName())
                .lastName(update.getMessage().getFrom().getLastName())
                .userName(update.getMessage().getFrom().getUserName())
                .text(update.getMessage().getText())
                .build();
    }

    @Builder
    public static UserDTO createRegisterUserDTO(Update update) {
        return UserDTO.builder()
                .chatId(update.getMessage().getChatId())
                .firstName(update.getMessage().getFrom().getFirstName())
                .lastName(update.getMessage().getFrom().getLastName())
                .userName(update.getMessage().getFrom().getUserName())
                .phone(update.getMessage().getContact().getPhoneNumber())
                .build();
    }
}
