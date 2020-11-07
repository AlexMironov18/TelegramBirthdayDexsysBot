package com.dexsys.TelegramBotDexsys.domain.services.entities;

import com.dexsys.TelegramBotDexsys.app.clientService.telegramHandlers.DTO.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;

@Builder
@Data
@Entity
@Table(name = "usersTable")
@AllArgsConstructor
public class User {

    public User() {
        super();
    }

    private UUID id;
    private String firstName;
    private String secondName;
    private String middleName;
    private Date birthDate;
    private String phone;
    @Id
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
