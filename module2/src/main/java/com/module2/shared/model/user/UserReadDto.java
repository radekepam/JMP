package com.module2.shared.model.user;

import com.module2.shared.config.security.ApplicationUserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserReadDto {

    private final Long id;
    private final String username;
    private final String email;
    private final ApplicationUserRole userRole;

    public UserReadDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.userRole = user.getUserRole();
    }
}
