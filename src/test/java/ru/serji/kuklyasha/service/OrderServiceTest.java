package ru.serji.kuklyasha.service;

import org.hibernate.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.transaction.annotation.*;
import ru.serji.kuklyasha.error.*;
import ru.serji.kuklyasha.model.Order;
import ru.serji.kuklyasha.model.*;

import javax.validation.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static ru.serji.kuklyasha.OrderTestData.NOT_FOUND;
import static ru.serji.kuklyasha.OrderTestData.getNew;
import static ru.serji.kuklyasha.OrderTestData.getUpdated;
import static ru.serji.kuklyasha.OrderTestData.*;
import static ru.serji.kuklyasha.UserTestData.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Test
    void get() {
        Order actual = orderService.get(ORDER_ID, user).get();
        ORDER_MATCHER.assertMatch(actual, order);
        User actualUser = (User) Hibernate.unproxy(actual.getUser());
        USER_MATCHER.assertMatch(actualUser, user);
    }

    @Test
    void notFound() {
        assertTrue(orderService.get(NOT_FOUND, user).isEmpty());
    }

    @Test
    void notFoundWithWrongUser() {
        assertTrue(orderService.get(ORDER_ID, admin).isEmpty());
    }

    @Test
    void getAllByUser() {
        List<Order> actual = orderService.getAll(user);
        ORDER_MATCHER.assertMatch(actual, order, order1);
        actual.forEach(order -> {
            User actualUser = (User) Hibernate.unproxy(order.getUser());
            USER_MATCHER.assertMatch(actualUser, user);
        });
    }

    @Test
    void notFoundAllByUser() {
        assertTrue(orderService.getAll(admin).isEmpty());
    }

    @Test
    void create() {
        Order created = orderService.save(getNew(), user);
        int newId = created.id();
        Order newOrder = getNew();
        newOrder.setId(newId);
        ORDER_MATCHER.assertMatch(created, newOrder);
        USER_MATCHER.assertMatch(created.getUser(), newOrder.getUser());
        ORDER_MATCHER.assertMatch(orderService.get(newId, user).get(), newOrder);
    }

    @Test
    void createInvalid() {
        Order newOrder = getNew();
        newOrder.setStatus(null);
        assertThrows(ConstraintViolationException.class, () -> orderService.save(newOrder, user));
    }

    @Test
    void update() {
        Order updated = getUpdated();
        orderService.save(updated, user);
        Order actual = orderService.get(ORDER_ID, user).get();
        ORDER_MATCHER.assertMatch(actual, updated);
        User actualUser = (User) Hibernate.unproxy(actual.getUser());
        USER_MATCHER.assertMatch(actualUser, user);
    }

    @Test
    void updateInvalidWithWrongUser() {
        Order updated = getUpdated();
        assertThrows(IllegalRequestDataException.class, () -> orderService.save(updated, admin));
    }

    @Test
    void delete() {
        orderService.delete(ORDER_ID, user);
        assertThrows(IllegalRequestDataException.class, () -> orderService.delete(ORDER_ID, user));
        assertTrue(orderService.get(ORDER_ID, user).isEmpty());
    }

    @Test
    void deleteNotFound() {
        assertThrows(IllegalRequestDataException.class, () -> orderService.delete(NOT_FOUND, user));
    }

    @Test
    void deleteNotFoundWithWrongUser() {
        assertThrows(IllegalRequestDataException.class, () -> orderService.delete(ORDER_ID, admin));
    }
}