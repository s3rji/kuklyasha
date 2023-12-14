package ru.serji.kuklyasha.service;

import org.hibernate.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.transaction.annotation.*;
import ru.serji.kuklyasha.*;
import ru.serji.kuklyasha.error.*;
import ru.serji.kuklyasha.model.*;

import javax.validation.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static ru.serji.kuklyasha.CartItemTestData.*;
import static ru.serji.kuklyasha.UserTestData.user;
import static ru.serji.kuklyasha.UserTestData.admin;
import static ru.serji.kuklyasha.UserTestData.USER_MATCHER;

@SpringBootTest
@Transactional
class CartServiceTest {

    @Autowired
    private CartService cartService;

    @Test
    void get() {
        CartItem actual = cartService.get(CART_ITEM_ID, user).get();
        CART_ITEM_MATCHER.assertMatch(actual, cartItem);
        User actualUser = (User) Hibernate.unproxy(actual.getUser());
        USER_MATCHER.assertMatch(actualUser, user);
    }

    @Test
    void notFound() {
        assertTrue(cartService.get(NOT_FOUND, user).isEmpty());
    }

    @Test
    void notFoundWithWrongUser() {
        assertTrue(cartService.get(CART_ITEM_ID, admin).isEmpty());
    }

    @Test
    void getAllByUser() {
        List<CartItem> actual = cartService.getAll(user);
        CART_ITEM_MATCHER.assertMatch(actual, allCartItems);
        actual.forEach(cartItem -> {
            User actualUser = (User) Hibernate.unproxy(cartItem.getUser());
            USER_MATCHER.assertMatch(actualUser, user);
        });
    }

    @Test
    void notFoundAllByUser() {
        assertTrue(cartService.getAll(admin).isEmpty());
    }

    @Test
    void create() {
        CartItem created = cartService.save(getNew(), admin);
        int newId = created.id();
        CartItem newCartItem = getNew();
        newCartItem.setId(newId);
        CART_ITEM_MATCHER.assertMatch(created, newCartItem);
        USER_MATCHER.assertMatch(created.getUser(), newCartItem.getUser());
        CART_ITEM_MATCHER.assertMatch(cartService.get(newId, admin).get(), newCartItem);
    }

    @Test
    void createInvalid() {
        CartItem newCartItem = new CartItem(null, DollTestData.doll, admin, 0);
        assertThrows(ConstraintViolationException.class, () -> cartService.save(newCartItem, admin));
    }

    @Test
    void createInvalidWithDuplicateDollAndUser() {
        CartItem newCartItem = getInvalidNew();
        assertThrows(IllegalRequestDataException.class, () -> cartService.save(newCartItem, user));
    }

    @Test
    void update() {
        CartItem updated = getUpdated();
        cartService.save(updated, user);
        CartItem actual = cartService.get(CART_ITEM_ID, user).get();
        CART_ITEM_MATCHER.assertMatch(actual, updated);
        User actualUser = (User) Hibernate.unproxy(actual.getUser());
        USER_MATCHER.assertMatch(actualUser, user);
    }

    @Test
    void updateInvalidWithWrongUser() {
        CartItem updated = getUpdated();
        assertThrows(IllegalRequestDataException.class, () -> cartService.save(updated, admin));
    }

    @Test
    void delete() {
        cartService.delete(CART_ITEM_ID, user);
        assertThrows(IllegalRequestDataException.class, () -> cartService.delete(CART_ITEM_ID, user));
        assertTrue(cartService.get(CART_ITEM_ID, user).isEmpty());
    }

    @Test
    void deleteNotFound() {
        assertThrows(IllegalRequestDataException.class, () -> cartService.delete(NOT_FOUND, user));
    }
}