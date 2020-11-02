package com.dexsys.TelegramBotDexsys.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;

public class ApiResponseException extends HttpServerErrorException {

    public ApiResponseException(HttpStatus statusCode, String statusText) {
        super(statusCode, statusText);
    }

}
