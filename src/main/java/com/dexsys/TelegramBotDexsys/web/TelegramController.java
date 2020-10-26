package com.dexsys.TelegramBotDexsys.web;

import com.dexsys.TelegramBotDexsys.repositories.IRepository;
import com.dexsys.TelegramBotDexsys.services.ITelegramService;
import com.dexsys.TelegramBotDexsys.services.entities.User;
import com.dexsys.TelegramBotDexsys.services.implementation.TelegramService;
import com.dexsys.TelegramBotDexsys.web.dtos.UserDtoWeb;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        final List<UserDtoWeb> userDto = users.stream().map(mapToUserDto).collect(toList());
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/{phone}")
    public HttpEntity<UserDtoWeb> getUser(@PathVariable("phone") String phoneNumber) {
        final User user;
        try {
            user = service.getUser(phoneNumber);
        } catch (RuntimeException e) {
            throw new NullPointerException();
        }
        final UserDtoWeb userDto = mapToUserDto.apply(user);
        return ResponseEntity.ok(userDto);
    }

    @DeleteMapping("/delete/{phone}")
    public HttpEntity<?> deleteUser(@PathVariable("phone") String phoneNumber) {
        final boolean isUserDeleted;
        try {
            isUserDeleted = service.deleteUser(phoneNumber);
        } catch (RuntimeException e) {
            throw new NullPointerException();
        }
        return isUserDeleted ? new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/sayhi")
    public String sayHi(){
        return "Hello World!";
    }

    private Function<User, UserDtoWeb> mapToUserDto = it ->
            UserDtoWeb.builder()
                    .birthDate(it.getBirthDate())
                    .chatId(it.getChatId())
                    .firstName(it.getFirstName())
                    .lastName(it.getLastName())
                    .middleName(it.getMiddleName())
                    .id(it.getId())
                    .isMale(it.isMale())
                    .phone(it.getPhone())
                    .userName(it.getUserName())
                    .build();
}
