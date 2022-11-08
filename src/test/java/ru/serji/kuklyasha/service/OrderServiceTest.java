package ru.serji.kuklyasha.service;

import org.hibernate.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.transaction.annotation.*;
import ru.serji.kuklyasha.*;
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
        Order actual = orderService.get(ORDER_ID).get();
        ORDER_MATCHER.assertMatch(actual, order);
        User actualUser = (User) Hibernate.unproxy(actual.getUser());
        USER_MATCHER.assertMatch(actualUser, UserTestData.user);
    }

    @Test
    void notFound() {
        assertTrue(orderService.get(NOT_FOUND).isEmpty());
    }

    @Test
    void getAllByUser() {
        List<Order> actual = orderService.getAllByUser(UserTestData.user);
        ORDER_MATCHER.assertMatch(actual, order);
        actual.forEach(order -> {
            User actualUser = (User) Hibernate.unproxy(order.getUser());
            USER_MATCHER.assertMatch(actualUser, UserTestData.user);
        });
    }

    @Test
    void notFoundByUser() {
        assertTrue(orderService.getAllByUser(UserTestData.admin).isEmpty());
    }

    @Test
    void create() {
        Order created = orderService.save(getNew());
        int newId = created.id();
        Order newOrder = getNew();
        newOrder.setId(newId);
        ORDER_MATCHER.assertMatch(created, newOrder);
        USER_MATCHER.assertMatch(created.getUser(), newOrder.getUser());
        ORDER_MATCHER.assertMatch(orderService.get(newId).get(), newOrder);
    }

    @Test
    void createInvalid() {
        Order newOrder = getNew();
        newOrder.setStatus(null);
        assertThrows(ConstraintViolationException.class, () -> orderService.save(newOrder));
    }

    @Test
    void update() {
        Order updated = getUpdated();
        orderService.save(updated);
        Order actual = orderService.get(ORDER_ID).get();
        ORDER_MATCHER.assertMatch(actual, updated);
        User actualUser = (User) Hibernate.unproxy(actual.getUser());
        USER_MATCHER.assertMatch(actualUser, UserTestData.user);
    }

    @Test
    void delete() {
        orderService.delete(ORDER_ID);
        assertThrows(IllegalRequestDataException.class, () -> orderService.delete(ORDER_ID));
        assertTrue(orderService.get(ORDER_ID).isEmpty());
    }

    @Test
    void deleteNotFound() {
        assertThrows(IllegalRequestDataException.class, () -> orderService.delete(NOT_FOUND));
    }
}