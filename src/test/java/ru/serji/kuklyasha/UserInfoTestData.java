package ru.serji.kuklyasha;

import ru.serji.kuklyasha.model.*;

import static ru.serji.kuklyasha.UserTestData.*;

public class UserInfoTestData {

    public static final MatcherFactory.Matcher<UserInfo> USER_INFO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(UserInfo.class, "user.created", "user.password");

    public static final int USER_INFO_ID = 1;

    public static final int NOT_FOUND = 1000;

    public static final Address userAddress = new Address("Россия", "Москва", "Москва", "главная д.5", "123456");

    public static final UserInfo userInfo = new UserInfo(user.id(), user, "Pupkin", "+79201112233", userAddress);

    public static UserInfo getNew() {
        return new UserInfo(null, null, "New", "+79001112233", userAddress);
    }

    public static UserInfo getUpdated() {
        return new UserInfo(user.id(), user, "Updated", "+79001112244", userAddress);
    }
}