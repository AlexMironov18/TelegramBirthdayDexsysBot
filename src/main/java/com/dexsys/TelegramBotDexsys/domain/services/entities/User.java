package com.dexsys.TelegramBotDexsys.domain.services.entities;

import com.dexsys.TelegramBotDexsys.app.clientService.telegramHandlers.DTO.UserDTO;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class User {

    private long chatId;
    private String userName;
    private String birthDate;
    private long id;
    private boolean isMale;
    private String firstName;
    private String lastName;
    private String middleName;
    private String phone;

    @Builder
    public static User createUser(UserDTO userDTO) {
        return User.builder()
                .chatId(userDTO.getChatId())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .userName(userDTO.getUserName())
                .phone(userDTO.getPhone())
                .build();
    }
}
