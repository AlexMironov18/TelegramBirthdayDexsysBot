package com.dexsys.TelegramBotDexsys.domain.repositories.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDbDTO {

    private UUID id;
    private String firstName;
    private String secondName;
    private String middleName;
    private String birthDate;
    private String phone;
    private String chatId;
    private boolean isMale;
}
