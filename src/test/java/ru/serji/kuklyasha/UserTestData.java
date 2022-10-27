package ru.serji.kuklyasha;

import ru.serji.kuklyasha.dto.*;
import ru.serji.kuklyasha.model.*;
import ru.serji.kuklyasha.web.util.*;

import java.time.*;
import java.util.*;

public class UserTestData {

    public static final MatcherFactory.Matcher<User> USER_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(User.class, "password", "created");

    public static final MatcherFactory.Matcher<UserTo> USER_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(UserTo.class, "password");

    public static final int USER_ID = 1;

    public static final int ADMIN_ID = 2;

    public static final int NOT_FOUND = 1000;

    public static final String USER_EMAIL = "user@yandex.ru";
    public static final String USER_PHONE = "79201112233";

    public static final String ADMIN_EMAIL = "admin@gmail.com";
    public static final String ADMIN_PHONE = "79251112233";


    public static final Address userAddress = new Address("Россия", "Москва", "Москва", "главная д.5", "123456");

    public static final UserInfo userInfo = new UserInfo("Pupkin", USER_PHONE, userAddress);

    public static final User user = new User(USER_ID, "User", USER_EMAIL, "password", userInfo, Role.USER);

    public static final Address adminAddress = new Address("Россия", "Одинцово", "Московская обл.", "вторая д.10", "123456");

    public static final UserInfo adminInfo = new UserInfo("Sidorov", ADMIN_PHONE, adminAddress);

    public static final User admin = new User(ADMIN_ID, "Admin", ADMIN_EMAIL, "admin", adminInfo, Role.ADMIN, Role.USER);

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", getNewUserInfo(), true, LocalDateTime.now(), Collections.singleton(Role.USER));
    }

    public static UserInfo getNewUserInfo() {
        return new UserInfo("newLastname", "71112223344", getNewAddress());
    }

    public static Address getNewAddress() {
        return new Address("newCountry", "newCity", "newRegion", "newStreet", "777888");
    }

    public static User getUpdated() {
        return new User(USER_ID, "UpdatedName", USER_EMAIL, "newPass", getNewUserInfo(), true, LocalDateTime.now(), Collections.singleton(Role.ADMIN));
    }

    public static String jsonWithPassword(UserTo userTo, String password) {
        return JsonUtil.writeAdditionProps(userTo, "password", password);
    }

    public static String jsonFromObject(UserTo userTo) {
        return JsonUtil.writeValue(userTo);
    }
}