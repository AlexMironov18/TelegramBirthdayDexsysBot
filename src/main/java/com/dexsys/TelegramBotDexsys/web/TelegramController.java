package com.dexsys.TelegramBotDexsys.web;

import com.dexsys.TelegramBotDexsys.repositories.IRepository;
import com.dexsys.TelegramBotDexsys.services.ITelegramService;
import com.dexsys.TelegramBotDexsys.services.entities.User;
import com.dexsys.TelegramBotDexsys.web.dtos.UserDtoWeb;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class TelegramController {

    private final IRepository service;

    @GetMapping
    public HttpEntity<List<UserDtoWeb>> getUsers() {
        final List<User> users;
        try {
            users = service.getUserList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        final List<UserDtoWeb> result = users.stream().map(mapFromUser).collect(toList());
        return ResponseEntity.ok(result);
    }

    @RequestMapping("/")
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
