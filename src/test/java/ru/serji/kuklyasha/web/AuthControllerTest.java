package ru.serji.kuklyasha.web;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.*;
import ru.serji.kuklyasha.dto.*;
import ru.serji.kuklyasha.model.*;
import ru.serji.kuklyasha.security.jwt.*;
import ru.serji.kuklyasha.service.*;
import ru.serji.kuklyasha.util.*;

import java.io.*;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.serji.kuklyasha.UserTestData.*;
import static ru.serji.kuklyasha.web.AuthController.*;

class AuthControllerTest extends AbstractControllerTest {

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    @Autowired
    public AuthControllerTest(JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @Test
    void login() throws Exception {
        AuthRequest authRequest = new AuthRequest(USER_EMAIL, "password", false);
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + "/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(authRequest)))
                .andDo(print())
                .andExpect(status().isOk());

        assertResultActionsContent(action);
    }

    @Test
    void checkAuth() throws Exception {
        User user = userService.getByEmailIgnoreCase(USER_EMAIL).get();
        String jwtToken = jwtTokenProvider.createToken(user);
        ResultActions action = perform(MockMvcRequestBuilders.get(REST_URL)
                .header(HttpHeaders.AUTHORIZATION, "Bearer_" + jwtToken))
                .andDo(print())
                .andExpect(status().isOk());

        assertResultActionsContent(action);
    }

    private void assertResultActionsContent(ResultActions action) throws UnsupportedEncodingException {
        String actionContent = action.andReturn().getResponse().getContentAsString();
        AuthResponse actual = JsonUtil.readValue(actionContent, AuthResponse.class);
        assertThat(actual.getEmail()).isEqualTo(USER_EMAIL);
    }

    @Test
    void loginInvalid() throws Exception {
        AuthRequest authRequest = new AuthRequest(USER_EMAIL, "wrongPassword", false);
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + "/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(authRequest)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void checkAuthInvalid() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .header(HttpHeaders.AUTHORIZATION, "wrongToken"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}