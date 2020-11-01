package com.dexsys.TelegramBotDexsys.app.clientService.mockClient;


import com.dexsys.TelegramBotDexsys.app.clientService.mockClient.mockDTO.UserMockDTO;
import com.dexsys.TelegramBotDexsys.services.IMockClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;


//при вводе телефона идет get запрос если он !=empty то авторизуем пользователя и добавляем в локальный репозиторий
//данные из json ответа (только те поля, которые в локальном репоитории null)
@Service
public class MockClient implements IMockClient {

    final RestTemplate restTemplate = new RestTemplate();

    @Override
    public UserMockDTO getUser(UUID uuid) {

        UserMockDTO response = restTemplate.getForObject(
                    "https://serene-coast-56441.herokuapp.com/api/users/" + uuid,
                    UserMockDTO.class);
        return response;
    }

    @Override
    public List<UserMockDTO> getAll() {
        ResponseEntity<UserMockDTO[]> responseEntity = restTemplate.getForEntity(
                "https://serene-coast-56441.herokuapp.com/api/users", UserMockDTO[].class);
        UserMockDTO[] objects = responseEntity.getBody();
        return Arrays.asList(objects);
    }

    @Override
    public UserMockDTO generateUser() {
        HttpEntity<UserMockDTO> request = new HttpEntity<>(new UserMockDTO());
        HttpEntity<UserMockDTO> response = restTemplate.postForEntity(
                "https://serene-coast-56441.herokuapp.com/api/users/generate",
                request,
                UserMockDTO.class);
        return response.getBody();
    }

    @Override
    public String createUser(UserMockDTO userMockDTO) {

        HttpEntity<UserMockDTO> request = new HttpEntity<>(userMockDTO);
        ResponseEntity<String> response = restTemplate.postForEntity("https://serene-coast-56441.herokuapp.com/api/users",
                request, String.class);
        return "Добавлен пользователь с id: \n" + response.getBody();

//        HttpEntity<UserMockDTO> request = new HttpEntity<>(userMockDTO);
//        UserMockDTO response = restTemplate.postForObject(
//                "https://serene-coast-56441.herokuapp.com/api/users",
//                request,
//                 UserMockDTO.class);
//        return response;
    }

    @Override
    public Set<HttpMethod> getOptions(UUID uuid) {
        return restTemplate.optionsForAllow("https://serene-coast-56441.herokuapp.com/api/users" + uuid);
    }
}
