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
@Table(name = "users")
@AllArgsConstructor
public class User {

    public User() {
        super();
    }

    @Column(name = "id")
    private UUID id;
    @Column(name = "firstname")
    private String firstName;
    @Column(name = "secondname")
    private String secondName;
    @Column(name = "middlename")
    private String middleName;
    @Column(name = "birthdate")
    private Date birthDate;
    @Column(name = "phone")
    private String phone;
    @Id
    @Column(name = "chatid")
    private String chatId;
    @Column(name = "ismale")
    private boolean isMale;

    @Builder
    public static User createUser(UserDTO userDTO) {
        return User.builder()
                .chatId(userDTO.getChatId())
                .phone(userDTO.getPhone())
                .build();
    }
}
