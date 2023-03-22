package com.module2.shared.error;

import com.module2.shared.error.errors.EmailExistException;
import com.module2.shared.error.errors.UserNotExistException;
import com.module2.shared.error.errors.UsernameExistException;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GeneralControllerAdvice extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GeneralControllerAdvice.class);

    @ExceptionHandler(value = ConstraintViolationException.class)
    ResponseEntity<ErrorModel> handleConstraintValidationException(ConstraintViolationException e) {
        LOGGER.error(e.getMessage());
        ErrorModel error = new ErrorModel(HttpStatus.BAD_REQUEST, ErrorMessage.VALIDATION_ERROR.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UserNotExistException.class)
    ResponseEntity<ErrorModel> handleNoSuchElementException(UserNotExistException e) {
        LOGGER.error(e.getMessage());
        ErrorModel error = new ErrorModel(HttpStatus.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = EmailExistException.class)
    ResponseEntity<ErrorModel> handleEmailExistException(EmailExistException e) {
        LOGGER.error(e.getMessage());
        ErrorModel error = new ErrorModel(HttpStatus.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UsernameExistException.class)
    ResponseEntity<ErrorModel> handleUsernameExistException(UsernameExistException e) {
        LOGGER.error(e.getMessage());
        ErrorModel error = new ErrorModel(HttpStatus.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
