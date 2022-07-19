package ru.serji.kuklyasha.service;

import ru.serji.kuklyasha.*;
import ru.serji.kuklyasha.model.*;

import java.time.*;
import java.util.*;

public class UserTestData {

    public static final MatcherFactory.Matcher<User> USER_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(User.class, "password", "created");
    public static final int USER_ID = 1;

    public static final int ADMIN_ID = 2;

    public static final int NOT_FOUND = 1000;

    public static final String USER_EMAIL = "user@yandex.ru";

    public static final String ADMIN_EMAIL = "admin@gmail.com";

    public static final User user = new User(USER_ID, "User", USER_EMAIL, "password", Role.USER);

    public static final User admin = new User(ADMIN_ID, "Admin", ADMIN_EMAIL, "admin", Role.ADMIN, Role.USER);

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", false, LocalDateTime.now(), Collections.singleton(Role.USER));
    }

    public static User getUpdated() {
        return new User(USER_ID, "UpdatedName", USER_EMAIL, "newPass", false, LocalDateTime.now(), Collections.singleton(Role.ADMIN));
    }
}
