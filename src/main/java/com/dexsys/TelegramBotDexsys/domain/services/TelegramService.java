package com.dexsys.TelegramBotDexsys.domain.services;

import com.dexsys.TelegramBotDexsys.app.clientService.telegramHandlers.RepeaterHandler;
import com.dexsys.TelegramBotDexsys.app.clientService.telegramHandlers.DTO.UserDTO;
import com.dexsys.TelegramBotDexsys.domain.repositories.UserRepository;
import com.dexsys.TelegramBotDexsys.services.IRepository;
import com.dexsys.TelegramBotDexsys.services.ITelegramService;
import com.dexsys.TelegramBotDexsys.domain.services.entities.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.util.List;


@Service
@Slf4j
public class TelegramService implements ITelegramService {

    private User user;
    private boolean toSetBDate = false;
    @Autowired
    private IRepository userRepository;
    @Autowired
    private RepeaterHandler handler;

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
    public void processMessage(UserDTO userDTO) throws TelegramApiException {
        user = User.createUser(userDTO);
        String birthDate;
        //during authorizing, userDTO has a phone number
        if (userDTO.getPhone() != null) userRepository.addUser(user);
        if (toSetBDate) {
            birthDate = userDTO.getText();
            setBirthDay(user, birthDate);
            toSetBDate = false;
            log.info("Установлена дата рождения \"{}\" для пользователя {}", userDTO.getText(),
                    ((UserRepository) userRepository).getChatIdMap().get(user.getChatId()));
        } else if (userDTO.getText().equals("Ввести дату рождения")) {
            toSetBDate = true;
        }
    }

    @Override
    public synchronized void setBirthDay(User user, String birthDate) throws TelegramApiException {
        ((UserRepository) userRepository).getUserMap()
                .get(((UserRepository) userRepository).getChatIdMap().get(user.getChatId())).setBirthDate(birthDate);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.getUserList();
    }

    @Override
    public User getUser(String userName) {
        return userRepository.getUser(userName);
    }

    @Override
    public boolean deleteUser(String phoneNUmber) {
        return userRepository.deleteUser(phoneNUmber);
    }
}
