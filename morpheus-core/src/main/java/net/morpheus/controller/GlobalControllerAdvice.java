package net.morpheus.controller;

import net.morpheus.exception.UserNotInCauthException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class GlobalControllerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(GlobalControllerAdvice.class);

    @ExceptionHandler(UserNotInCauthException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public void handleUserNotInCauth(UserNotInCauthException exception) {
        logger.error(String.format("User [%s] not in Cauth", exception.username()), exception);
    }

}
