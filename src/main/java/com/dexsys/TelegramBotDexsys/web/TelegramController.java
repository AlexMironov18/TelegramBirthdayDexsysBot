package com.dexsys.TelegramBotDexsys.web;

import com.dexsys.TelegramBotDexsys.services.ITelegramService;
import com.dexsys.TelegramBotDexsys.services.entities.User;
import com.dexsys.TelegramBotDexsys.web.dtos.UserDtoWeb;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value = "find all users", notes = "returns a list of users")
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
    @ApiOperation(value = "find the user with this id", notes = "returns a user with a given id")
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

    @DeleteMapping("/{phone}")
    @ApiOperation(value = "delete the user with this id", notes = "deletes a user with a given id")
    public HttpEntity<?> deleteUser(@PathVariable("phone") String phoneNumber) {
        final boolean isUserDeleted;
        try {
            isUserDeleted = service.deleteUser(phoneNumber);
        } catch (RuntimeException e) {
            throw new NullPointerException();
        }
        return isUserDeleted ? new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.NOT_FOUND);
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
