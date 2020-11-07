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
import java.util.List;
import java.util.function.Function;


@Service
@Slf4j
public class TelegramService implements ITelegramService {

    private User user;
    @Autowired
    private IRepeaterHandler handler;
    @Autowired
    private IWebProxyService proxyService;
    @Autowired
    private ITelegramReplyService telegramReplyService;
    @Autowired
    private IUserRepository repository;


    @Override
    public synchronized SendMessage processAuthorizationMessage(UserDTO userDTO) throws TelegramApiException {
        user = User.createUser(userDTO);
        User userToAuthorize = proxyService.getUsers().stream()
                .filter(userWebDTO -> userWebDTO.getPhone() != null && userWebDTO.getPhone().equals(user.getPhone()))
                .findAny().map(mapperToUser).orElse(null);
        if (userToAuthorize != null) {
            userToAuthorize.setChatId(user.getChatId());
            repository.save(userToAuthorize);
            return telegramReplyService.sendMsg(userDTO);
        } else {
            userDTO.setText("Пользователь не найден");
            return telegramReplyService.sendMsg(userDTO);
        }
    }

    @Override
    public synchronized SendMessage processMessage(UserDTO userDTO) throws TelegramApiException {
        if (userDTO.getText().equals("Очистить профиль")) deleteUser(userDTO.getChatId());
        return telegramReplyService.sendMsg(userDTO);
    }

    @Override
    public List<User> getUsers() {
        return (List<User>) repository.findAll();
    }

    @Override
    public User getUser(String chatId) {
        if (isUserExist(chatId)) {
            return repository.findById(chatId).get();
        } else return null;
    }

    @Override
    public boolean deleteUser(String chatId) {
        if (isUserExist(chatId)) {
            repository.deleteById(chatId);
            return true;
        } else return false;
    }

    @Override
    public boolean isUserExist(String chatId) {
        return repository.existsById(chatId);
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
