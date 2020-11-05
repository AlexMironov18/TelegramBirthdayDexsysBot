package com.dexsys.TelegramBotDexsys.domain.services;

import com.dexsys.TelegramBotDexsys.app.clientService.telegramHandlers.RepeaterHandler;
import com.dexsys.TelegramBotDexsys.app.clientService.telegramHandlers.DTO.UserDTO;
import com.dexsys.TelegramBotDexsys.app.web.webDTO.UserWebDTO;
import com.dexsys.TelegramBotDexsys.services.IRepository;
import com.dexsys.TelegramBotDexsys.services.ITelegramReplyService;
import com.dexsys.TelegramBotDexsys.services.ITelegramService;
import com.dexsys.TelegramBotDexsys.domain.services.entities.User;
import com.dexsys.TelegramBotDexsys.services.IWebProxyService;
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
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;


@Service
@Slf4j
public class TelegramService implements ITelegramService {


    @Autowired
    private IRepository userRepository;
    @Autowired
    private RepeaterHandler handler;
    @Autowired
    private IWebProxyService proxyService;
    @Autowired
    private ITelegramReplyService telegramReplyService;

    private User user;

    @Override
    @PostConstruct
    public void setupBot() {
        TelegramBotsApi botsApi = new TelegramBotsApi();
        try {
            botsApi.registerBot(handler);
        } catch (TelegramApiException e) {
            log.error("setupBot: " + e.toString());
        }
    }

    @Override
    public synchronized SendMessage processAuthorizationMessage(UserDTO userDTO) throws TelegramApiException {
        user = User.createUser(userDTO);
        User userToAuthorize = proxyService.getUsers().stream()
                .filter(userWebDTO -> userWebDTO.getPhone() != null && userWebDTO.getPhone().equals(user.getPhone()))
                .findAny().map(mapperToUser).orElse(null);
        if (userToAuthorize != null) {
            userToAuthorize.setChatId(user.getChatId());
            addUser(userToAuthorize);
            return telegramReplyService.sendMsg(userDTO);
        } else {
            userDTO.setText("Пользователь не найден");
            return telegramReplyService.sendMsg(userDTO);
        }
    }

    boolean setBirthday = false;
    @Override
    public synchronized SendMessage processMessage(UserDTO userDTO) throws TelegramApiException {
        if (userDTO.getText().equals("Очистить профиль")) deleteUser(userDTO.getChatId());
        if (userDTO.getText().equals("Ввести дату рождения")) {
            setBirthday = true;
            userDTO.setText("Введите вашу дату рождения в формате: January 1, 1975");
            return telegramReplyService.sendMsg(userDTO);
        }
        if (setBirthday) {
            User userToSetBithdate = new User();
            userToSetBithdate.setChatId(userDTO.getChatId());
            userToSetBithdate.setBirthDate(userDTO.getText());
            addUser(userToSetBithdate);
            setBirthday = false;
            return telegramReplyService.sendMsg(userDTO);
        }
        return telegramReplyService.sendMsg(userDTO);
    }

    @Override
    public Integer addUser(User user) {
        return userRepository.addUser(mapperToUserWebDTO.apply(user));
    }

    @Override
    public List<UserWebDTO> getUsers() {
        return userRepository.getUserList();
    }

    @Override
    public UserWebDTO getUser(String chatId) {
        return userRepository.getUser(chatId);
    }

    @Override
    public Integer deleteUser(String chatId) {
        return userRepository.deleteUser(chatId);
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

    private Function<User, UserWebDTO> mapperToUserWebDTO = it -> UserWebDTO.builder()
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
