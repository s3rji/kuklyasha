package ru.serji.kuklyasha.web.order;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.test.context.support.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.*;
import ru.serji.kuklyasha.dto.*;
import ru.serji.kuklyasha.model.Order;
import ru.serji.kuklyasha.model.*;
import ru.serji.kuklyasha.service.*;
import ru.serji.kuklyasha.web.*;
import ru.serji.kuklyasha.web.util.*;

import java.util.*;
import java.util.stream.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.serji.kuklyasha.OrderTestData.NOT_FOUND;
import static ru.serji.kuklyasha.OrderTestData.getNew;
import static ru.serji.kuklyasha.OrderTestData.jsonFromObject;
import static ru.serji.kuklyasha.OrderTestData.*;
import static ru.serji.kuklyasha.UserTestData.*;
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
        Iterator<PurchasedItemTo> createdToIter = created.getItems().iterator();
        Iterator<PurchasedItemTo> newOrderToIter = newTo.getItems().iterator();
        while (createdToIter.hasNext() && newOrderToIter.hasNext()) {
            newOrderToIter.next().setId(createdToIter.next().id());
        }

        ORDER_TO_MATCHER.assertMatch(created, newTo);
    }

    @Test
    @WithUserDetails(value = USER_EMAIL)
    void createInvalid() throws Exception {
        OrderTo newTo = new OrderTo(null, Collections.emptyList(), null, null, null);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFromObject(newTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_EMAIL)
    void createFailedBecauseNotEnoughQuantity() throws Exception {
        Order newOrder = getInvalidNew();
        OrderTo newTo = createToFromOrder(newOrder);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFromObject(newTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_EMAIL)
    void update() throws Exception {
        StatusTo statusTo = new StatusTo(StatusType.DONE);
        perform(MockMvcRequestBuilders.patch(REST_URL + ORDER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(statusTo)))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertEquals(statusTo.getType(), orderService.get(ORDER_ID, user).get().getStatus().getType());
    }

    @Test
    @WithUserDetails(value = USER_EMAIL)
    void updateInvalid() throws Exception {
        StatusTo statusTo = new StatusTo(null);
        perform(MockMvcRequestBuilders.patch(REST_URL + ORDER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(statusTo)))
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
    }
}