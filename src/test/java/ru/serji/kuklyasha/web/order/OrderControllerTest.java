package ru.serji.kuklyasha.web.order;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.test.context.support.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.*;
import ru.serji.kuklyasha.dto.*;
import ru.serji.kuklyasha.model.Order;
import ru.serji.kuklyasha.service.*;
import ru.serji.kuklyasha.web.*;
import ru.serji.kuklyasha.web.util.*;

import java.util.*;
import java.util.stream.*;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.serji.kuklyasha.OrderTestData.*;
import static ru.serji.kuklyasha.UserTestData.USER_EMAIL;
import static ru.serji.kuklyasha.UserTestData.user;
import static ru.serji.kuklyasha.web.util.OrderUtil.*;

class OrderControllerTest extends AbstractControllerTest {

    private static final String REST_URL = OrderController.REST_URL + "/";

    @Autowired
    private OrderService orderService;

    @Test
    @WithUserDetails(value = USER_EMAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + ORDER_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(ORDER_TO_MATCHER.contentJson(createToFromOrder(order)));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + ORDER_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_EMAIL)
    void notFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + NOT_FOUND))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = USER_EMAIL)
    void getAllByUser() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(ORDER_TO_MATCHER.contentJson(Stream.of(order, order1).map(OrderUtil::createToFromOrder).toList()));
    }

    @Test
    @WithUserDetails(value = USER_EMAIL)
    void create() throws Exception {
        Order newOrder = getNew();
        OrderTo newTo = createToFromOrder(newOrder);
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFromObject(newTo)))
                .andDo(print())
                .andExpect(status().isCreated());

        OrderTo created = ORDER_TO_MATCHER.readFromJson(action);
        int newId = created.id();
        newTo.setId(newId);
        ORDER_TO_MATCHER.assertMatch(created, newTo);

        newOrder.setId(newId);
        ORDER_MATCHER.assertMatch(orderService.get(newId, user).get(), newOrder);
    }

    @Test
    @WithUserDetails(value = USER_EMAIL)
    void createInvalid() throws Exception {
        OrderTo newTo = new OrderTo(null, Collections.emptySet(), null, null, null);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFromObject(newTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_EMAIL)
    void update() throws Exception {
        Order updatedOrder = getUpdated();
        OrderTo updatedTo = createToFromOrder(updatedOrder);
        perform(MockMvcRequestBuilders.put(REST_URL + ORDER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFromObject(updatedTo)))
                .andDo(print())
                .andExpect(status().isNoContent());

        ORDER_MATCHER.assertMatch(orderService.get(ORDER_ID, user).get(), updatedOrder);
    }

    @Test
    @WithUserDetails(value = USER_EMAIL)
    void updateInvalid() throws Exception {
        OrderTo updatedTo = new OrderTo(null, Collections.emptySet(), null, null, null);
        perform(MockMvcRequestBuilders.put(REST_URL + ORDER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFromObject(updatedTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_EMAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + ORDER_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        ORDER_MATCHER.assertMatch(orderService.getAll(user), order1);
        assertTrue(orderService.get(ORDER_ID, user).isEmpty());
    }

    @Test
    @WithUserDetails(value = USER_EMAIL)
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + NOT_FOUND))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
        assertTrue(orderService.get(NOT_FOUND, user).isEmpty());
    }
}