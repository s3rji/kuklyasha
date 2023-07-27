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
    void getByIdAndUser() {
        Order actual = orderService.getByIdAndUser(ORDER_ID, user).get();
        ORDER_MATCHER.assertMatch(actual, order);
        User actualUser = (User) Hibernate.unproxy(actual.getUser());
        USER_MATCHER.assertMatch(actualUser, user);
    }

    @Test
    void getById() {
        Order actual = orderService.getById(ORDER_ID).get();
        ORDER_MATCHER.assertMatch(actual, order);
    }

    @Test
    void notFound() {
        assertTrue(orderService.getByIdAndUser(NOT_FOUND, user).isEmpty());
    }

    @Test
    void notFoundWithWrongUser() {
        assertTrue(orderService.getByIdAndUser(ORDER_ID, admin).isEmpty());
    }

    @Test
    void getAll() {
        List<Order> actual = orderService.getAllFetchUser();
        ORDER_MATCHER.assertMatch(actual, order, order1, order2);
    }

    @Test
    void getAllByUser() {
        List<Order> actual = orderService.getAllByUser(user);
        ORDER_MATCHER.assertMatch(actual, order, order1);
        actual.forEach(order -> {
            User actualUser = (User) Hibernate.unproxy(order.getUser());
            USER_MATCHER.assertMatch(actualUser, user);
        });
    }

    @Test
    void getLimitFetchUserAndSortByIdAsc() {
        List<Order> actual = orderService.getLimitFetchUserAndSort(0, 5, "id", "asc");
        ORDER_MATCHER.assertMatch(actual, order, order1, order2);
    }

    @Test
    void getLimitFetchUserAndSortByIdDesc() {
        List<Order> actual = orderService.getLimitFetchUserAndSort(0, 5, "id", "desc");
        ORDER_MATCHER.assertMatch(actual, order2, order1, order);
    }

    @Test
    void getLimitFetchUserAndSortByUserAsc() {
        List<Order> actual = orderService.getLimitFetchUserAndSort(0, 5, "user", "asc");
        ORDER_MATCHER.assertMatch(actual, order, order1, order2);
    }

    @Test
    void getLimitFetchUserAndSortByUserDesc() {
        List<Order> actual = orderService.getLimitFetchUserAndSort(0, 5, "user", "desc");
        ORDER_MATCHER.assertMatch(actual, order2, order, order1);
    }

    @Test
    void getLimitFetchUserAndSortByUserNameAsc() {
        List<Order> actual = orderService.getLimitFetchUserAndSort(0, 5, "user.name", "asc");
        ORDER_MATCHER.assertMatch(actual, order2, order, order1);
    }

    @Test
    void getLimitByFilterId() {
        List<Order> actual = orderService.getLimitByFilterFetchUserAndSort(0, 5, "id", "asc", "id", "2")
                .getContent();
        ORDER_MATCHER.assertMatch(actual, order1);
    }

    @Test
    void getLimitByFilterUser() {
        List<Order> actual = orderService.getLimitByFilterFetchUserAndSort(0, 5, "id", "desc", "user.name", "uSe")
                .getContent();
        ORDER_MATCHER.assertMatch(actual, order1, order);
    }

    @Test
    void getLimitByFilterUserPageTwo() {
        List<Order> actual = orderService.getLimitByFilterFetchUserAndSort(1, 1, "id", "desc", "user.name", "uSe")
                .getContent();
        ORDER_MATCHER.assertMatch(actual, order);
    }

    @Test
    void getLimitByFilterStatus() {
        List<Order> actual = orderService.getLimitByFilterFetchUserAndSort(0, 5, "user.name", "asc", "status", "done")
                .getContent();
        ORDER_MATCHER.assertMatch(actual, order2, order1);
    }

    @Test
    void failedGetLimitByFilterStatus() {
        assertThrows(IllegalRequestDataException.class,
                () -> orderService.getLimitByFilterFetchUserAndSort(0, 5, "user.name", "asc", "status", "complete"));
    }

    @Test
    void failedGetLimitByFilterId() {
        assertThrows(IllegalRequestDataException.class,
                () -> orderService.getLimitByFilterFetchUserAndSort(0, 5, "user.name", "asc", "id", "one"));
    }

    @Test
    void create() {
        Order created = orderService.create(getNew());
        int newOrderId = created.id();
        Order newOrder = getNew();
        newOrder.setId(newOrderId);
        Iterator<PurchasedItem> createdIter = created.getItems().iterator();
        Iterator<PurchasedItem> newOrderIter = newOrder.getItems().iterator();
        while (createdIter.hasNext() && newOrderIter.hasNext()) {
            newOrderIter.next().setId(createdIter.next().id());
        }

        ORDER_MATCHER.assertMatch(created, newOrder);
        USER_MATCHER.assertMatch(created.getUser(), newOrder.getUser());
        ORDER_MATCHER.assertMatch(orderService.getByIdAndUser(newOrderId, user).get(), newOrder);
    }

    @Test
    void createInvalid() {
        Order invalid = new Order(null, null, null);
        assertThrows(ConstraintViolationException.class, () -> orderService.create(invalid));
    }

    @Test
    void update() {
        Order updated = getUpdated();
        orderService.update(updated);
        Order actual = orderService.getById(ORDER_ID).get();
        ORDER_MATCHER.assertMatch(actual, updated);
    }

    @Test
    void delete() {
        orderService.delete(ORDER_ID, user);
        assertThrows(IllegalRequestDataException.class, () -> orderService.delete(ORDER_ID, user));
        assertTrue(orderService.getByIdAndUser(ORDER_ID, user).isEmpty());
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