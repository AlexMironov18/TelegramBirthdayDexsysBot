package com.dexsys.TelegramBotDexsys.app.web;

import com.dexsys.TelegramBotDexsys.app.clientService.mockClient.mockDTO.UserMockDTO;
import com.dexsys.TelegramBotDexsys.services.ITelegramService;
import com.dexsys.TelegramBotDexsys.app.web.webDTO.UserWebDTO;
import com.dexsys.TelegramBotDexsys.services.IWebProxyService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    private IWebProxyService webProxyService;

    @GetMapping
    @ApiOperation(value = "Find all users", notes = "Returns a list of users")
    public ResponseEntity<List<UserWebDTO>> getUsers() {
        return ResponseEntity.ok(webProxyService.getUsers());
    }

    @GetMapping("/{uuid}")
    @ApiOperation(value = "Find a user by id", notes = "Returns a user with a given id")
    public ResponseEntity<UserWebDTO> getUser(@PathVariable("uuid") UUID uuid) {
        return ResponseEntity.ok(webProxyService.getUser(uuid));
    }

    @PostMapping
    @ApiOperation(value = "Create a user", notes = "Creates a user with given information")
    public ResponseEntity<String> createUser(@RequestBody UserWebDTO user) {
        return ResponseEntity.ok(webProxyService.createUser(user));
    }

    @PostMapping("/generate")
    @ApiOperation(value = "Generate a user", notes = "Generates a user with random information")
    public ResponseEntity<UserMockDTO> generateUser() {
        return ResponseEntity.ok(webProxyService.generateUser());
    }

    @RequestMapping(value = "/{uuid}", method = RequestMethod.OPTIONS)
    @ApiOperation(value = "Find allowed options to the user", notes = "Returns a list of the options")
    public ResponseEntity<Set<HttpMethod>> getOptions(@PathVariable("uuid") UUID uuid) {
        return ResponseEntity.ok(webProxyService.getOptions(uuid));
    }
}
