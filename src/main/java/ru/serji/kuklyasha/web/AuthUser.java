package ru.serji.kuklyasha.web;

import lombok.*;
import org.springframework.lang.NonNull;
import ru.serji.kuklyasha.model.*;

@Getter
@ToString(onlyExplicitlyIncluded = true)
public class AuthUser extends org.springframework.security.core.userdetails.User {

    @ToString.Include
    private final User user;

    public AuthUser(@NonNull User user) {
        super(user.getEmail(), user.getPassword(), user.getRoles());
        this.user = user;
    }

    public int id() {
        return user.id();
    }
}