package com.dexsys.TelegramBotDexsys.app.clientService.mockClient.mockDTO;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import com.fasterxml.jackson.databind.deser.std.UUIDDeserializer;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import com.fasterxml.jackson.databind.ser.std.UUIDSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserMockDTO implements Serializable {

    public UserMockDTO(String id) {
        this.id = UUID.fromString(id);
    }

    private UUID id;
    private String firstName;
    private String secondName;
    private String middleName;
    private Date birthDate;
    private String phone;
    private String chatId;
    private boolean isMale;

}
