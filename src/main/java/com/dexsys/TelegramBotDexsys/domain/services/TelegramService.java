package com.dexsys.TelegramBotDexsys.domain.services;

import com.dexsys.TelegramBotDexsys.app.clientService.telegramHandlers.RepeaterHandler;
import com.dexsys.TelegramBotDexsys.app.clientService.telegramHandlers.DTO.UserDTO;
import com.dexsys.TelegramBotDexsys.app.web.webDTO.UserWebDTO;
import com.dexsys.TelegramBotDexsys.services.*;
import com.dexsys.TelegramBotDexsys.domain.services.entities.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;


@Service
@Slf4j
public class TelegramService implements ITelegramService {

    @Autowired
    private IWebProxyService proxyService;
    @Autowired
    private ITelegramReplyService telegramReplyService;
    @Autowired
    private IDataService dataService;

    private boolean setBirthday = false;

    @Override
    public synchronized SendMessage processAuthorizationMessage(UserDTO userDTO) throws TelegramApiException {
        User user = User.createUser(userDTO);
        User userToAuthorize = proxyService.getUsers().stream()
                .filter(userWebDTO -> userWebDTO.getPhone() != null && userWebDTO.getPhone().equals(user.getPhone()))
                .findAny().map(mapperToUser).orElse(null);
        if (userToAuthorize != null) {
            userToAuthorize.setChatId(user.getChatId());
            dataService.addUser(userToAuthorize);
            return telegramReplyService.sendMsg(userDTO);
        } else {
            userDTO.setText("Пользователь не найден");
            return telegramReplyService.sendMsg(userDTO);
        }
    }

    @Override
    public synchronized SendMessage processMessage(UserDTO userDTO) throws TelegramApiException {
        if (setBirthday) {
            DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
            User userToSetBirthdate = new User();
            userToSetBirthdate.setChatId(userDTO.getChatId());
            try {
                userToSetBirthdate.setBirthDate(format.parse(userDTO.getText()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            dataService.updateUser(userToSetBirthdate);
            setBirthday = false;
            userDTO.setText("Дата рождения введена");
            return telegramReplyService.sendMsg(userDTO);
        }
        switch (userDTO.getText()) {
            case "Очистить профиль":
                dataService.deleteUser(userDTO.getChatId());
                break;
            case "Изменить дату рождения" :
                setBirthday = true;
                userDTO.setText("Введите вашу дату рождения в формате: January 1, 1975");
                return telegramReplyService.sendMsg(userDTO);
        }
        return telegramReplyService.sendMsg(userDTO);
    }

    private Function<UserWebDTO, User> mapperToUser = it -> User.builder()
            .birthDate(it.getBirthDate())
            .chatId(it.getChatId())
            .firstName(it.getFirstName())
            .secondName(it.getSecondName())
            .middleName(it.getMiddleName())
            .id(it.getId())
            .isMale(it.isMale())
            .phone(it.getPhone())
            .build();
}
