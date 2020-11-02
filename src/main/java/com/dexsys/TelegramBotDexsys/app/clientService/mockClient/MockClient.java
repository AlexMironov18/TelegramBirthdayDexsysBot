package com.dexsys.TelegramBotDexsys.app.clientService.mockClient;


import com.dexsys.TelegramBotDexsys.app.clientService.mockClient.mockDTO.UserMockDTO;
import com.dexsys.TelegramBotDexsys.app.exceptions.ApiRequestException;
import com.dexsys.TelegramBotDexsys.app.exceptions.ApiResponseException;
import com.dexsys.TelegramBotDexsys.services.IMockClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;


//при вводе телефона идет get запрос если он !=empty то авторизуем пользователя и добавляем в локальный репозиторий
//данные из json ответа (только те поля, которые в локальном репоитории null)

public class MockClient implements IMockClient {

    final RestTemplate restTemplate = new RestTemplate();

    @Override
    public UserMockDTO getUser(UUID uuid) throws HttpStatusCodeException {

        try {
            return restTemplate.getForObject(
                    "https://serene-coast-56441.herokuapp.com/api/users/" + uuid,
                    UserMockDTO.class);
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode().is4xxClientError())
                throw new ApiRequestException(e.getStatusCode(), "Пользователя с таким id не существует");
             else throw new ApiResponseException(e.getStatusCode(), "Ошибка сервера");

        }
    }

    @Override
    public List<UserMockDTO> getAll() throws HttpStatusCodeException {

        try {
            UserMockDTO[] responseEntity = restTemplate.getForObject(
                    "https://serene-coast-56441.herokuapp.com/api/users", UserMockDTO[].class);
            return Arrays.asList(responseEntity);
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode().is4xxClientError())
                throw new ApiRequestException(e.getStatusCode(), "Список пользователей пуст");
            else throw new ApiResponseException(e.getStatusCode(), "Ошибка сервера");
        }
    }

    @Override
    public UserMockDTO generateUser() throws HttpStatusCodeException {

        try {
            HttpEntity<UserMockDTO> request = new HttpEntity<>(new UserMockDTO());
            UserMockDTO response = restTemplate.postForObject(
                    "https://serene-coast-56441.herokuapp.com/api/users/generate",
                    request,
                    UserMockDTO.class);
            return response;
        } catch (HttpStatusCodeException e) {
            throw new ApiResponseException(e.getStatusCode(), "Ошибка сервера");
        }
    }

    @Override
    public String createUser(UserMockDTO userMockDTO) throws HttpStatusCodeException {

        try {
            HttpEntity<UserMockDTO> request = new HttpEntity<>(userMockDTO);
            ResponseEntity<String>response = restTemplate.postForEntity("https://serene-coast-56441.herokuapp.com/api/users",
                    request, String.class);
            return "Добавлен пользователь с id: \n" + response.getBody();
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode().is4xxClientError())
                throw new ApiRequestException(e.getStatusCode(), "Данные пользователя введены некрректно");
            else throw new ApiResponseException(e.getStatusCode(), "Ошибка сервера");
        }

//        Implement, when mockServer create-request is fixed (returns full user info, not only id)
//        HttpEntity<UserMockDTO> request = new HttpEntity<>(userMockDTO);
//        UserMockDTO response = restTemplate.postForObject(
//                "https://serene-coast-56441.herokuapp.com/api/users",
//                request,
//                 UserMockDTO.class);
//        return response;
    }

    @Override
    public Set<HttpMethod> getOptions(UUID uuid) throws HttpStatusCodeException {

        try {
            return restTemplate.optionsForAllow("https://serene-coast-56441.herokuapp.com/api/users/" + uuid);
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode().is4xxClientError())
                throw new ApiRequestException(e.getStatusCode(), "Пользователя с таким id не существует");
            else throw new ApiResponseException(e.getStatusCode(), "Ошибка сервера");
        }
    }
}
