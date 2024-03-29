package ru.serji.kuklyasha.web.cart;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.test.context.support.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.*;
import ru.serji.kuklyasha.*;
import ru.serji.kuklyasha.dto.cart.*;
import ru.serji.kuklyasha.model.*;
import ru.serji.kuklyasha.service.*;
import ru.serji.kuklyasha.web.*;
import ru.serji.kuklyasha.web.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.serji.kuklyasha.CartItemTestData.*;
import static ru.serji.kuklyasha.UserTestData.ADMIN_EMAIL;
import static ru.serji.kuklyasha.UserTestData.USER_EMAIL;
import static ru.serji.kuklyasha.UserTestData.admin;
import static ru.serji.kuklyasha.UserTestData.user;

class CartControllerTest extends AbstractControllerTest {

    private static final String REST_URL = CartController.REST_URL + "/";

    @Autowired
    private CartService cartService;

    @Test
    @WithUserDetails(value = USER_EMAIL)
    void getAllByUser() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(CART_ITEM_TO_MATCHER.contentJson(allCartItems.stream().map(CartUtil::createToFromCartItem).toList()));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    void create() throws Exception {
        CreatedCartItem cartItem = new CreatedCartItem(DollTestData.DOLL_ID);
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(cartItem)))
                .andDo(print())
                .andExpect(status().isCreated());

        CartItemTo created = CART_ITEM_TO_MATCHER.readFromJson(action);
        int newId = created.id();
        CartItem expected = getNew();
        expected.setId(newId);
        CART_ITEM_MATCHER.assertMatch(cartService.get(newId, admin).get(), expected);
    }

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    void createInvalidWithNullId() throws Exception {
        CreatedCartItem invalid = new CreatedCartItem(null);
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    void createInvalidWithNonExistentDoll() throws Exception {
        CreatedCartItem invalid = new CreatedCartItem(DollTestData.NOT_FOUND);
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_EMAIL)
    void update() throws Exception {
        UpdatedCartItem updatedTo = new UpdatedCartItem(1, 2);
        perform(MockMvcRequestBuilders.patch(REST_URL + CART_ITEM_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isNoContent());

        CART_ITEM_MATCHER.assertMatch(cartService.get(CART_ITEM_ID, user).get(), getUpdated());
    }

    @Test
    @WithUserDetails(value = USER_EMAIL)
    void updateInvalid() throws Exception {
        UpdatedCartItem invalid = new UpdatedCartItem(1, 0);
        perform(MockMvcRequestBuilders.patch(REST_URL + CART_ITEM_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_EMAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + CART_ITEM_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertTrue(cartService.get(CART_ITEM_ID, user).isEmpty());
    }

    @Test
    @WithUserDetails(value = USER_EMAIL)
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + NOT_FOUND))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}