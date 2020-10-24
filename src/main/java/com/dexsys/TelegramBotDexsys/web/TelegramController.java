package com.dexsys.TelegramBotDexsys.web;

import com.dexsys.TelegramBotDexsys.repositories.IRepository;
import com.dexsys.TelegramBotDexsys.services.ITelegramService;
import com.dexsys.TelegramBotDexsys.services.entities.User;
import com.dexsys.TelegramBotDexsys.services.implementation.TelegramService;
import com.dexsys.TelegramBotDexsys.web.dtos.UserDtoWeb;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class TelegramController {

    @Autowired
    private ITelegramService service;

    @GetMapping
    public HttpEntity<List<UserDtoWeb>> getUsers() {
        final List<User> users;
        try {
            users = service.getUsers();
        } catch (RuntimeException e) {
           throw new NullPointerException();
        }
        final List<UserDtoWeb> userDto = users.stream().map(mapFromUser).collect(toList());
        return ResponseEntity.ok(userDto);
    }
    //если мапить user -> user.tostring то он отображается в брауезере, если просто возвращать user - то нет
    @GetMapping("/{userName}")
    public HttpEntity<String> getUser(@PathVariable("userName") String userName) {
        final User user;
        try {
            user = service.getUser(userName);
        } catch (RuntimeException e) {
            throw new NullPointerException();
        }
        return ResponseEntity.ok(user.toString());
    }
    @RequestMapping("/sayhi")
    public String home(){
        return "Hello World!";
    }

    private Function<User, UserDtoWeb> mapFromUser = it ->
            UserDtoWeb.builder()
                    .isMale(it.isMale())
                    .phone(it.getPhone())
                    .firstName(it.getFirstName())
                    .lastName(it.getLastName())
                    .middleName(it.getMiddleName())
                    .userName(it.getUserName())
                    .birthDate(it.getBirthDate())
                    .build();
}
