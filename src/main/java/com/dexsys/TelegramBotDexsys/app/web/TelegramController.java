package com.dexsys.TelegramBotDexsys.app.web;

import com.dexsys.TelegramBotDexsys.app.clientService.mockClient.mockDTO.UserMockDTO;
import com.dexsys.TelegramBotDexsys.services.ITelegramService;
import com.dexsys.TelegramBotDexsys.app.web.webDTO.UserWebDTO;
import com.dexsys.TelegramBotDexsys.services.IWebProxyService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class TelegramController {

    @Autowired
    private ITelegramService service;

    @Autowired
    private IWebProxyService webProxyService;

    @GetMapping
    @ApiOperation(value = "find all users", notes = "returns a list of users")
    public ResponseEntity<List<UserWebDTO>> getUsers() {

        final List<UserWebDTO> users;
        users = webProxyService.getUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{uuid}")
    @ApiOperation(value = "find the user with this id", notes = "returns a user with a given id")
    public ResponseEntity<UserWebDTO> getUser(@PathVariable("uuid") UUID uuid) {

        final UserWebDTO userWebDTO;
        try {
            userWebDTO = webProxyService.getUser(uuid);
        } catch (RuntimeException e) {
            throw new NullPointerException();
        }
        return ResponseEntity.ok(userWebDTO);
    }

    @PostMapping
    @ApiOperation(value = "create a user", notes = "creates a user")
    public ResponseEntity<String> createUser(@RequestBody UserWebDTO user) {
        final UserWebDTO userWebDTO = user;
        return ResponseEntity.ok(webProxyService.createUser(userWebDTO));
    }

    @PostMapping("/generate")
    @ApiOperation(value = "create a user", notes = "creates a user")
    public ResponseEntity<UserMockDTO> generateUser() {
        return ResponseEntity.ok(webProxyService.generateUser());
    }

    @RequestMapping(value = "/{uuid}", method = RequestMethod.OPTIONS)
    public ResponseEntity<Set<HttpMethod>> getOptions(@PathVariable("uuid") UUID uuid) {
        return ResponseEntity.ok(webProxyService.getOptions(uuid));
    }
}
