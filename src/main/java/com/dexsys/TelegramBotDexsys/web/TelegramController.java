package com.dexsys.TelegramBotDexsys.web;

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

@RestController
@AllArgsConstructor
public class TelegramController {

    private ITelegramService service;

    @GetMapping("/getusers")
    public HttpEntity<List<UserDtoWeb>> getUsers() {

        List<User> users = null;

        try {
            users = service.getUsers();
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<UserDtoWeb> result = users.stream().map(mapFromUser).collect(Collectors.toList());
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
