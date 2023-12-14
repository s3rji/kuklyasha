package ru.serji.kuklyasha.web.admin;

import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.http.*;
import org.springframework.security.test.context.support.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.*;
import ru.serji.kuklyasha.*;
import ru.serji.kuklyasha.dto.*;
import ru.serji.kuklyasha.dto.order.*;
import ru.serji.kuklyasha.events.*;
import ru.serji.kuklyasha.model.Order;
import ru.serji.kuklyasha.model.*;
import ru.serji.kuklyasha.service.*;
import ru.serji.kuklyasha.web.*;
import ru.serji.kuklyasha.web.util.*;

import java.util.*;
import java.util.stream.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.serji.kuklyasha.OrderTestData.*;
import static ru.serji.kuklyasha.UserTestData.*;

public class AdminControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminController.REST_URL + "/";
    private static final String ORDERS_URL = REST_URL + "orders";
    private static final String USERS_URL = REST_URL + "users";

    @MockBean
    private NotificationSourceBean notification;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    void getOrder() throws Exception {
        perform(MockMvcRequestBuilders.get(ORDERS_URL + "/" + ORDER_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(ADMIN_ORDER_TO_MATCHER.contentJson(OrderUtil.createAdminToFromOrder(order)));
    }

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    void getAllOrders() throws Exception {
        perform(MockMvcRequestBuilders.get(ORDERS_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(ADMIN_ORDER_TO_MATCHER.contentJson(Stream.of(order, order1, order2).map(OrderUtil::createAdminToFromOrder).toList()));
    }

    @Test
    @WithUserDetails(value = USER_EMAIL)
    void getAllOrdersForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(ORDERS_URL))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    void getLimitFetchUserAndSort() throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.get(ORDERS_URL)
                .param("page", "0")
                .param("limit", "5")
                .param("sort", "user.name")
                .param("direction", "asc"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        AdminOrderPage actual = JsonUtil.readValue(action.andReturn().getResponse().getContentAsString(), AdminOrderPage.class);
        ADMIN_ORDER_TO_MATCHER.assertMatch(actual.getContent(), Stream.of(order2, order, order1).map(OrderUtil::createAdminToFromOrder).toList());
    }

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    void failedLimitFetchUserAndSort() throws Exception {
        perform(MockMvcRequestBuilders.get(ORDERS_URL)
                .param("page", "0")
                .param("limit", "5")
                .param("sort", "delivery")
                .param("direction", "asc"))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    void getLimitByFilterUsername() throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.get(ORDERS_URL)
                .param("page", "0")
                .param("limit", "5")
                .param("sort", "id")
                .param("direction", "asc")
                .param("field", "user.name")
                .param("filter", "uSe"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        AdminOrderPage actual = JsonUtil.readValue(action.andReturn().getResponse().getContentAsString(), AdminOrderPage.class);
        ADMIN_ORDER_TO_MATCHER.assertMatch(actual.getContent(), Stream.of(order, order1).map(OrderUtil::createAdminToFromOrder).toList());
    }

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    void failedLimitByFilterUsername() throws Exception {
        perform(MockMvcRequestBuilders.get(ORDERS_URL)
                .param("page", "0")
                .param("limit", "5")
                .param("sort", "id")
                .param("direction", "asc")
                .param("field", "delivery")
                .param("filter", "10.05.2022"))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    void updateOrder() throws Exception {
        Mockito.doNothing().when(notification).publishNotification("", "", "", 0, null, "");
        OrderChangeTo orderChange = new OrderChangeTo(OrderTestData.ORDER_ID, StatusType.DONE, "27.07.2023");
        Order expected = new Order(order);
        OrderUtil.updateOrderFromOrderChange(expected, orderChange);
        perform(MockMvcRequestBuilders.patch(ORDERS_URL + "/" + ORDER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(orderChange)))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertEquals(expected, orderService.getById(ORDER_ID).get());
    }

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    void failedUpdateOrder() throws Exception {
        Mockito.doNothing().when(notification).publishNotification("", "", "", 0, null, "");
        OrderChangeTo orderChange = new OrderChangeTo(ORDER_ID, null, null);
        perform(MockMvcRequestBuilders.patch(ORDERS_URL + "/" + ORDER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(orderChange)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    void getAllUsers() throws Exception {
        perform(MockMvcRequestBuilders.get(USERS_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_TO_MATCHER.contentJson(Stream.of(user, admin, disabled).map(UserUtil::createToFromUser).toList()));
    }

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    void getLimitByPage() throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.get(USERS_URL)
                .param("page", "0")
                .param("limit", "2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        UserPage actual = JsonUtil.readValue(action.andReturn().getResponse().getContentAsString(), UserPage.class);
        USER_TO_MATCHER.assertMatch(actual.getContent(), Stream.of(user, admin).map(UserUtil::createToFromUser).toList());
        assertThat(actual.getTotal()).isEqualTo(List.of(user, admin, disabled).size());
    }

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    void getUser() throws Exception {
        perform(MockMvcRequestBuilders.get(USERS_URL + "/" + USER_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_TO_MATCHER.contentJson(UserUtil.createToFromUser(user)));
    }

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    void getUserOrders() throws Exception {
        perform(MockMvcRequestBuilders.get(USERS_URL + "/" + USER_ID + "/orders"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(ADMIN_ORDER_TO_MATCHER.contentJson(Stream.of(order, order1).map(OrderUtil::createAdminToFromOrder).toList()));
    }

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    void updateUser() throws Exception {
        UserChangeTo userChange = new UserChangeTo(USER_ID, false);
        User expected = new User(user);
        expected.setEnabled(userChange.isEnabled());
        perform(MockMvcRequestBuilders.patch(USERS_URL + "/" + USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(userChange)))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertEquals(expected, userService.get(USER_ID).get());
    }

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    void failedUpdateUser() throws Exception {
        OrderChangeTo orderChange = new OrderChangeTo(null, null, null);
        perform(MockMvcRequestBuilders.patch(USERS_URL + "/" + USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(orderChange)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
