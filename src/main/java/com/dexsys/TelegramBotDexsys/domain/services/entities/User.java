package com.dexsys.TelegramBotDexsys.domain.services.entities;

import com.dexsys.TelegramBotDexsys.app.clientService.telegramHandlers.DTO.UserDTO;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Builder
@Data
public class User {


    private UUID id;
    private String firstName;
    private String secondName;
    private String middleName;
    private Date birthDate;
    private String phone;
    private String chatId;
    private boolean isMale;


    @Builder
    public static User createUser(UserDTO userDTO) {
        return User.builder()
                .chatId(userDTO.getChatId())
                .phone(userDTO.getPhone())
                .build();
    }
}
