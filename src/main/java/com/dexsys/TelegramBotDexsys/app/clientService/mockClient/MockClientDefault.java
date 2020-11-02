package com.dexsys.TelegramBotDexsys.app.clientService.mockClient;

import com.dexsys.TelegramBotDexsys.app.clientService.mockClient.mockDTO.UserMockDTO;
import com.dexsys.TelegramBotDexsys.app.exceptions.ApiRequestException;
import com.dexsys.TelegramBotDexsys.app.exceptions.ApiResponseException;
import com.dexsys.TelegramBotDexsys.services.IMockClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class MockClientDefault implements IMockClient {

    @Override
    public UserMockDTO getUser(UUID uuid) {
        throw new ApiResponseException(HttpStatus.SERVICE_UNAVAILABLE, "Используется default сервер." );
    }

    @Override
    public List<UserMockDTO> getAll() {
        throw new ApiResponseException(HttpStatus.SERVICE_UNAVAILABLE, "Используется default сервер.");
    }

    @Override
    public String createUser(UserMockDTO userMockDTO) {
        throw new ApiResponseException(HttpStatus.SERVICE_UNAVAILABLE, "Используется default сервер.");
    }

    @Override
    public UserMockDTO generateUser() {
        throw new ApiResponseException(HttpStatus.SERVICE_UNAVAILABLE, "Используется default сервер.");
    }

    @Override
    public Set<HttpMethod> getOptions(UUID uuid) {
        throw new ApiResponseException(HttpStatus.SERVICE_UNAVAILABLE, "Используется default сервер.");
    }
}
