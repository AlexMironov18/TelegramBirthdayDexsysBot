package com.dexsys.TelegramBotDexsys.services.entities;

import com.dexsys.TelegramBotDexsys.clientServices.DTO.UserDTO;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class User {

    private long chatId;
    private String userName;
    private String bDate;

    @Builder
    public static User createUser(UserDTO userDTO) {
        return User.builder().chatId(userDTO.getChatId())
                .userName(userDTO.getUserName())
                .build();
    }
}
