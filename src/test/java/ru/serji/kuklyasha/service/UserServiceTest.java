package ru.serji.kuklyasha.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.dao.*;
import org.springframework.transaction.annotation.*;
import ru.serji.kuklyasha.error.*;
import ru.serji.kuklyasha.model.*;

import javax.validation.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static ru.serji.kuklyasha.UserTestData.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void get() {
        User actual = userService.get(USER_ID).get();
        USER_MATCHER.assertMatch(actual, user);
    }

    @Test
    void getAdmin() {
        User actual = userService.get(ADMIN_ID).get();
        USER_MATCHER.assertMatch(actual, admin);
    }

    @Test
    void notFound() {
        assertTrue(userService.get(NOT_FOUND).isEmpty());
    }

    @Test
    void getByEmailIgnoreCase() {
        User actual = userService.getByEmailIgnoreCase(USER_EMAIL).get();
        USER_MATCHER.assertMatch(actual, user);
    }

    @Test
    void getByEmailNotFound() {
        assertTrue(userService.getByEmailIgnoreCase("").isEmpty());
    }

    @Test
    void getAll() {
        List<User> actual = userService.getAll();
        USER_MATCHER.assertMatch(actual, user, admin);
    }

    @Test
    void create() {
        User created = userService.save(getNew());
        int newId = created.id();
        User newUser = getNew();
        newUser.setId(newId);
        USER_MATCHER.assertMatch(created, newUser);
        USER_MATCHER.assertMatch(userService.get(newId).get(), newUser);
    }

    @Test
    void createInvalid() {
        User newUser = getNew();
        newUser.setName("");
        assertThrows(ConstraintViolationException.class, () -> userService.save(newUser));
    }

    @Test
    void update() {
        User updatedUser = getUpdated();
        userService.save(updatedUser);
        USER_MATCHER.assertMatch(userService.get(USER_ID).get(), getUpdated());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void duplicateEmailUpdated() {
        User duplicateUpdated = getUpdated();
        duplicateUpdated.setEmail(ADMIN_EMAIL);
        assertThrows(DataAccessException.class, () -> userService.save(duplicateUpdated));
    }

    @Test
    void delete() {
        userService.delete(USER_ID);
        assertThrows(IllegalRequestDataException.class, () -> userService.delete(USER_ID));
        assertTrue(userService.get(USER_ID).isEmpty());
    }

    @Test
    void deleteNotFound() {
        assertThrows(IllegalRequestDataException.class, () -> userService.delete(NOT_FOUND));
    }
}