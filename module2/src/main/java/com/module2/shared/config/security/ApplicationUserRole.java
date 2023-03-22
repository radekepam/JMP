package com.module2.shared.config.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public enum ApplicationUserRole {
    USER(List.of()),
    ADMIN(List.of(ApplicationUserPermission.values()));

    private final Collection<ApplicationUserPermission> userPermissions;

    ApplicationUserRole(List<ApplicationUserPermission> userPermissions) {
        this.userPermissions = userPermissions;
    }

    public Collection<SimpleGrantedAuthority> getPermissions() {
        List<SimpleGrantedAuthority> permissions = this.userPermissions
                .stream().map(permission -> new SimpleGrantedAuthority(permission.getUserPermission()))
                .collect(Collectors.toList());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
