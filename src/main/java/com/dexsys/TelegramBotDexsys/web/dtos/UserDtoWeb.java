package com.dexsys.TelegramBotDexsys.web.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
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
