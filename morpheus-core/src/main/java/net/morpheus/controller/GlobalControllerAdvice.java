package net.morpheus.controller;

import net.morpheus.domain.MorpheusError;
import net.morpheus.exception.UnauthorisedAccessException;
import net.morpheus.exception.UserAlreadyExistsException;
import net.morpheus.exception.UserNotInCauthException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ControllerAdvice
public class GlobalControllerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(GlobalControllerAdvice.class);

    @ExceptionHandler({UserNotInCauthException.class, UserAlreadyExistsException.class})
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ResponseBody
    public MorpheusError handleUserNotInCauth(UserNotInCauthException exception) {
        logger.error(exception.getMessage(), exception);
        return new MorpheusError(exception.getMessage());
    }

    @ExceptionHandler({UnauthorisedAccessException.class})
    @ResponseStatus(UNAUTHORIZED)
    @ResponseBody
    public MorpheusError handleUnauthorisedAccess(UnauthorisedAccessException exception) {
        logger.error(exception.getMessage(), exception);
        return new MorpheusError(exception.getMessage());
    }


}
