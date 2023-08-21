package ru.serji.kuklyasha.service;

import ru.serji.kuklyasha.model.*;

import java.util.*;

public interface UserService {
    Optional<User> get(int id);

    Optional<User> getByEmailIgnoreCase(String email);

    Optional<User> getByPhone(String phone);

    List<User> getAll();

    List<User> getLimitByPage(int page, int limit);

    User save(User user);

    void delete(int id);

    int totalCount();
}
