package com.module2.shared.error;

public enum ErrorMessage {
    EMAIL_EXIST_EXCEPTION("User with given email exists"),
    USER_EXIST_EXCEPTION("User with given username exists"),
    INVALID_USERNAME_OR_PASSWORD("Invalid username or password"),
    NEED_TO_LOGIN("Need to log in to access this page"),
    ACCESS_DENIED("Not have permission to access this page"),
    VALIDATION_ERROR("Validation Error"),
    ;

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
