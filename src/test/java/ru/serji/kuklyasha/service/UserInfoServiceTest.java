package ru.serji.kuklyasha.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.dao.*;
import org.springframework.transaction.annotation.*;
import ru.serji.kuklyasha.*;
import ru.serji.kuklyasha.error.*;
import ru.serji.kuklyasha.model.*;

import javax.validation.*;

import static org.junit.jupiter.api.Assertions.*;
import static ru.serji.kuklyasha.UserInfoTestData.*;

@SpringBootTest
@Transactional
class UserInfoServiceTest {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserService userService;

    @Test
    void get() {
        UserInfo actual = userInfoService.get(USER_INFO_ID).get();
        UserInfoTestData.USER_INFO_MATCHER.assertMatch(actual, userInfo);
    }

    @Test
    void notFound() {
        assertTrue(userInfoService.get(NOT_FOUND).isEmpty());
    }

    @Test
    void getByUser() {
        UserInfo actual = userInfoService.getByUser(UserTestData.user).get();
        UserInfoTestData.USER_INFO_MATCHER.assertMatch(actual, userInfo);
    }

    @Test
    void getByUserNotFound() {
        User userNotFound = UserTestData.getNew();
        userNotFound.setId(3);
        assertTrue(userInfoService.getByUser(userNotFound).isEmpty());
    }

    @Test
    void create() {
        User newUser = userService.save(UserTestData.getNew());
        UserInfo newUserInfo = getNew(newUser);
        UserInfo created = userInfoService.save(newUserInfo);
        int newId = created.id();

        USER_INFO_MATCHER.assertMatch(created, newUserInfo);
        USER_INFO_MATCHER.assertMatch(userInfoService.get(newId).get(), newUserInfo);
    }

    @Test
    void createInvalidLastname() {
        User newUser = userService.save(UserTestData.getNew());
        UserInfo newUserInfo = getNew(newUser);
        newUserInfo.setLastname("");

        assertThrows(ConstraintViolationException.class, () -> userInfoService.save(newUserInfo));
    }

    @Test
    void createInvalidPhone() {
        User newUser = userService.save(UserTestData.getNew());
        UserInfo newUserInfo = getNew(newUser);
        newUserInfo.setPhone("+7921-28828-11");

        assertThrows(ConstraintViolationException.class, () -> userInfoService.save(newUserInfo));
    }

    @Test
    void createInvalidAddress() {
        User newUser = userService.save(UserTestData.getNew());
        UserInfo newUserInfo = getNew(newUser);
        newUserInfo.getAddress().setZipcode("123-456");

        assertThrows(ConstraintViolationException.class, () -> userInfoService.save(newUserInfo));
    }

    @Test
    void update() {
        UserInfo updated = getUpdated();
        userInfoService.save(updated);
        USER_INFO_MATCHER.assertMatch(userInfoService.get(USER_INFO_ID).get(), getUpdated());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void duplicatePhoneUpdated() {
        UserInfo duplicateUpdated = getUpdated();
        duplicateUpdated.setPhone("+79251112233");
        assertThrows(DataAccessException.class, () -> userInfoService.save(duplicateUpdated));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void duplicateUserUpdated() {
        UserInfo duplicateUpdated = getUpdatedDuplicate();
        assertThrows(DataAccessException.class, () -> userInfoService.save(duplicateUpdated));
    }

    @Test
    void delete() {
        userInfoService.delete(USER_INFO_ID);
        assertThrows(IllegalRequestDataException.class, () -> userInfoService.delete(USER_INFO_ID));
        assertTrue(userInfoService.get(USER_INFO_ID).isEmpty());
    }

    @Test
    void deleteNotFound() {
        assertThrows(IllegalRequestDataException.class, () -> userInfoService.delete(NOT_FOUND));
    }

    @Test
    void notFoundAfterUserWasDeleting() {
        userService.delete(UserTestData.USER_ID);
        assertTrue(userInfoService.get(USER_INFO_ID).isEmpty());
    }
}