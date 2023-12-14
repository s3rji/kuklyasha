package ru.serji.kuklyasha.model;

import org.springframework.security.core.*;

public enum Role implements GrantedAuthority {
    USER,
    ADMIN;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}