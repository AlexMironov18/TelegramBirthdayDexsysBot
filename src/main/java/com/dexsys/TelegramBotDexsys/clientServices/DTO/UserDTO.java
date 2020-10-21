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
    private long id;
    private boolean isMale;
    private String firstName;
    private String lastName;
    private String middleName;


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
}
