package com.dexsys.TelegramBotDexsys.services.implementation;

import com.dexsys.TelegramBotDexsys.handlers.RepeaterHandler;
import com.dexsys.TelegramBotDexsys.handlers.DTO.UserDTO;
import com.dexsys.TelegramBotDexsys.repositories.UserRepository;
import com.dexsys.TelegramBotDexsys.repositories.IRepository;
import com.dexsys.TelegramBotDexsys.services.ITelegramService;
import com.dexsys.TelegramBotDexsys.services.entities.User;
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
        userRepository.createAndAddUserToRepository(user);
        if (toSetBDate) {
            birthDate = userDTO.getText();
            setBirthDay(user, birthDate);
            toSetBDate = false;
            log.info("Установлена дата рождения \"{}\" для пользователя {}", userDTO.getText(), userDTO.getUserName());
        } else if (userDTO.getText().equals("Ввести дату рождения")) {
            toSetBDate = true;
        }
    }

    //setting birthdate of the user
    @Override
    public synchronized void setBirthDay(User user, String birthDate) throws TelegramApiException {
        ((UserRepository) userRepository).getUserMap().get(user.getUserName()).setBirthDate(birthDate);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.getUserList();
    }

}
