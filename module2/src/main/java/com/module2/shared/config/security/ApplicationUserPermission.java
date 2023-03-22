package com.module2.shared.config.security;

import lombok.Getter;

@Getter
public enum ApplicationUserPermission {
    USER_CREATE("user:create"),
    USER_READ("user:read"),
    USER_WRITE("user:write");

    private final String userPermission;

    ApplicationUserPermission(String userPermission) {
        this.userPermission = userPermission;
    }
}
