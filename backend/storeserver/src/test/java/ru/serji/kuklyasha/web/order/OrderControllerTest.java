package ru.serji.kuklyasha.web.order;

import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.http.*;
import org.springframework.security.test.context.support.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.*;
import ru.serji.kuklyasha.dto.order.*;
import ru.serji.kuklyasha.events.*;
import ru.serji.kuklyasha.model.Order;
import ru.serji.kuklyasha.service.*;
import ru.serji.kuklyasha.web.*;
import ru.serji.kuklyasha.web.util.*;

import java.util.*;
import java.util.stream.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.serji.kuklyasha.OrderTestData.NOT_FOUND;
import static ru.serji.kuklyasha.OrderTestData.*;
import static ru.serji.kuklyasha.UserTestData.*;
import static ru.serji.kuklyasha.web.util.OrderUtil.*;

class OrderControllerTest extends AbstractControllerTest {

    private static final String REST_URL = OrderController.REST_URL + "/";

    @MockBean
    private NotificationSourceBean notification;

    @Autowired
    private OrderService orderService;

    @Autowired
    private DollService dollService;

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
        Mockito.doNothing().when(notification).publishNotification("", "", "", 0, null, "");
        List<PurchasedDoll> dolls = getNewPurchasedDolls();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(dolls)))
                .andDo(print())
                .andExpect(status().isCreated());

        OrderTo created = ORDER_TO_MATCHER.readFromJson(action);
        int newId = created.id();
        Order newOrder = getAfterCreating();
        OrderTo newTo = createToFromOrder(newOrder);
        newTo.setId(newId);
        Iterator<PurchasedItemTo> createdToIter = created.getItems().iterator();
        Iterator<PurchasedItemTo> newOrderToIter = newTo.getItems().iterator();
        while (createdToIter.hasNext() && newOrderToIter.hasNext()) {
            newOrderToIter.next().setId(createdToIter.next().id());
        }

        ORDER_TO_MATCHER.assertMatch(created, newTo);

        int dollQuantityInStock = dollService.get(created.getItems().iterator().next().id()).get().getQuantity();
        int dollQuantityExpected = created.getItems().iterator().next().getDoll().getQuantity();
        assertEquals(dollQuantityExpected, dollQuantityInStock);
    }

    @Test
    @WithUserDetails(value = USER_EMAIL)
    void createInvalid() throws Exception {
        List<PurchasedDoll> invalid = List.of(new PurchasedDoll(1, 0));
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_EMAIL)
    void createFailedBecauseNotEnoughQuantity() throws Exception {
        List<PurchasedDoll> invalid = List.of(new PurchasedDoll(1, 100));
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_EMAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + ORDER_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        ORDER_MATCHER.assertMatch(orderService.getAllByUser(user), order1);
        assertTrue(orderService.getByIdAndUser(ORDER_ID, user).isEmpty());
    }

    @Test
    @WithUserDetails(value = USER_EMAIL)
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + NOT_FOUND))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}