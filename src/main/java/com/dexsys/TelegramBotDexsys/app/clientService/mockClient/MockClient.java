package com.dexsys.TelegramBotDexsys.app.clientService.mockClient;


import com.dexsys.TelegramBotDexsys.app.clientService.mockClient.mockDTO.UserMockDTO;
import com.dexsys.TelegramBotDexsys.app.exceptions.ApiRequestException;
import com.dexsys.TelegramBotDexsys.app.exceptions.ApiResponseException;
import com.dexsys.TelegramBotDexsys.services.IMockClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;


@Slf4j
public class MockClient implements IMockClient {

    @Autowired
    private RestOperations restTemplate;

    @Value("${mockURL}")
    private String mockURL;

    @Override
    public UserMockDTO getUser(UUID uuid) throws HttpStatusCodeException {

        try {
            return restTemplate.getForObject(
                    mockURL + uuid,
                    UserMockDTO.class);
        } catch (HttpStatusCodeException e) {
            log.error("Ошибка при вызове \"getUser()\" Mock - сервера, код ошибки: {}", e.getStatusCode());
            if (e.getStatusCode().is4xxClientError())
                throw new ApiRequestException(e.getStatusCode(), "Пользователя с таким id не существует");
             else throw new ApiResponseException(e.getStatusCode(), "Ошибка сервера");

        }
    }

    @Override
    public List<UserMockDTO> getAll() throws HttpStatusCodeException {

        try {
            UserMockDTO[] responseEntity = restTemplate.getForObject(
                    mockURL, UserMockDTO[].class);
            return Arrays.asList(responseEntity);
        } catch (HttpStatusCodeException e) {
            log.error("Ошибка при вызове \"getAll()\" Mock - сервера, код ошибки: {}", e.getStatusCode());
            if (e.getStatusCode().is4xxClientError())
                throw new ApiRequestException(e.getStatusCode(), "Список пользователей пуст");
            else throw new ApiResponseException(e.getStatusCode(), "Ошибка сервера");
        }
    }

    @Override
    public UserMockDTO generateUser() throws HttpStatusCodeException {

        try {
            HttpEntity<UserMockDTO> request = new HttpEntity<>(new UserMockDTO());
            return restTemplate.postForObject(
                    mockURL + "generate",
                    request,
                    UserMockDTO.class);
        } catch (HttpStatusCodeException e) {
            log.error("Ошибка при вызове \"generateUser()\" Mock - сервера, код ошибки: {}", e.getStatusCode());
            throw new ApiResponseException(e.getStatusCode(), "Ошибка сервера");
        }
    }

    @Override
    public String createUser(UserMockDTO userMockDTO) throws HttpStatusCodeException {

        try {
            HttpEntity<UserMockDTO> request = new HttpEntity<>(userMockDTO);
            ResponseEntity<String>response = restTemplate.postForEntity(mockURL,
                    request, String.class);
            return "Добавлен пользователь с id: \n" + response.getBody();
        } catch (HttpStatusCodeException e) {
            log.error("Ошибка при вызове \"createUser()\" Mock - сервера, код ошибки: {}", e.getStatusCode());
            if (e.getStatusCode().is4xxClientError())
                throw new ApiRequestException(e.getStatusCode(), "Данные пользователя введены некрректно");
            else throw new ApiResponseException(e.getStatusCode(), "Ошибка сервера");
        }
    }

    @Override
    public Set<HttpMethod> getOptions(UUID uuid) throws HttpStatusCodeException {

        try {
            return restTemplate.optionsForAllow(mockURL + uuid);
        } catch (HttpStatusCodeException e) {
            log.error("Ошибка при вызове \"getOptions()\" Mock - сервера, код ошибки: {}", e.getStatusCode());
            if (e.getStatusCode().is4xxClientError())
                throw new ApiRequestException(e.getStatusCode(), "Пользователя с таким id не существует");
            else throw new ApiResponseException(e.getStatusCode(), "Ошибка сервера");
        }
    }
}
