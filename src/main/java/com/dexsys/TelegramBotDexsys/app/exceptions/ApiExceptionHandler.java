package com.dexsys.TelegramBotDexsys.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpStatusCodeException;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ApiRequestException.class, ApiResponseException.class})
    public ResponseEntity<Object> handleApiRequestException(HttpStatusCodeException e) {
        ApiException apiException = new ApiException(
                e.getStatusText(),
                e.getStatusCode(),
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException, e.getStatusCode());
    }
}
