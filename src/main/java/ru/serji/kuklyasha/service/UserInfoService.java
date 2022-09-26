package ru.serji.kuklyasha.service;

import ru.serji.kuklyasha.model.*;

import java.util.*;

public interface UserInfoService {
    Optional<UserInfo> get(int id);

    Optional<UserInfo> getByUser(User user);

    UserInfo save(UserInfo userInfo);

    void delete(int id);
}