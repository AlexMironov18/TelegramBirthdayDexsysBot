package com.dexsys.TelegramBotDexsys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
public class ModelGetSet {

    private String name;
    private String lastName;
    private boolean isMale;
    private int phoneNumber;

}
