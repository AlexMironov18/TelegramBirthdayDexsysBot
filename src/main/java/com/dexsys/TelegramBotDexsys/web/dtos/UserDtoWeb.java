package com.dexsys.TelegramBotDexsys.web.dtos;

import com.dexsys.TelegramBotDexsys.handlers.DTO.UserDTO;
import lombok.Builder;
import org.telegram.telegrambots.meta.api.objects.Update;

@Builder
public class UserDtoWeb {

    private long chatId;
    private String userName;
    private String birthDate;
    private long id;
    private boolean isMale;
    private String firstName;
    private String lastName;
    private String middleName;
    private String phone;

}
