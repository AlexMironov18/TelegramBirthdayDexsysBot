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
import java.util.List;
import java.util.function.Function;


@Service
@Slf4j
public class TelegramService implements ITelegramService {

    private User user;
    private boolean toSetBDate = false;
    @Autowired
    private IRepository userRepository;
    @Autowired
    private RepeaterHandler handler;
    @Autowired
    private IWebProxyService proxyService;
    @Autowired
    private ITelegramReplyService telegramReplyService;

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
                .filter(userWebDTO -> userWebDTO.getPhone().equals(user.getPhone()))
                .findAny().map(mapperToUser).orElse(null);
        if (userToAuthorize != null) {
            userToAuthorize.setChatId(user.getChatId());
            userRepository.addUser(userToAuthorize);
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
        return userRepository.getUserList();
    }

    @Override
    public User getUser(String chatId) {
        return userRepository.getUser(chatId);
    }

    @Override
    public boolean deleteUser(String chatId) {
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
}
