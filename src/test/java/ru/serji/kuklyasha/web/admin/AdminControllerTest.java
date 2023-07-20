package ru.serji.kuklyasha.web.admin;

import org.junit.jupiter.api.*;
import org.springframework.http.*;
import org.springframework.security.test.context.support.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.*;
import ru.serji.kuklyasha.dto.order.*;
import ru.serji.kuklyasha.web.*;
import ru.serji.kuklyasha.web.util.*;

import java.util.stream.*;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.serji.kuklyasha.OrderTestData.*;
import static ru.serji.kuklyasha.UserTestData.*;

public class AdminControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminController.REST_URL + "/";
    private static final String ORDERS_URL = REST_URL + "orders";

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(ORDERS_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(ADMIN_ORDER_TO_MATCHER.contentJson(Stream.of(order, order1, order2).map(OrderUtil::createAdminToFromOrder).toList()));
    }

    @Test
    @WithUserDetails(value = USER_EMAIL)
    void getAllForbidden() throws Exception {
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
}
