package ru.serji.kuklyasha.web.user;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.test.context.support.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.*;
import ru.serji.kuklyasha.dto.*;
import ru.serji.kuklyasha.model.*;
import ru.serji.kuklyasha.service.*;
import ru.serji.kuklyasha.web.*;
import ru.serji.kuklyasha.web.util.*;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.serji.kuklyasha.UserTestData.*;
import static ru.serji.kuklyasha.web.user.ProfileController.*;

class ProfileControllerTest extends AbstractControllerTest {

    @Autowired
    private UserService userService;

    @Test
    @WithUserDetails(value = USER_EMAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_TO_MATCHER.contentJson(UserUtil.createToFromUser(user)));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_EMAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL))
                .andExpect(status().isNoContent());
        USER_MATCHER.assertMatch(userService.getAll(), admin, disabled);
    }

    @Test
    void register() throws Exception {
        User newUser = getNew();
        UserTo newTo = UserUtil.createToFromUser(newUser);
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(newTo, "newPassword")))
                .andDo(print())
                .andExpect(status().isCreated());

        UserTo created = USER_TO_MATCHER.readFromJson(action);
        int newId = created.id();
        newTo.setId(newId);
        USER_TO_MATCHER.assertMatch(created, newTo);

        newUser.setId(newId);
        USER_MATCHER.assertMatch(userService.get(newId).get(), newUser);
    }

    @Test
    @WithUserDetails(value = USER_EMAIL)
    void update() throws Exception {
        User updatedUser = getUpdated();
        UserTo updatedTo = UserUtil.createToFromUser(updatedUser);
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(updatedTo, "newPassword")))
                .andDo(print())
                .andExpect(status().isNoContent());

        USER_MATCHER.assertMatch(userService.get(USER_ID).get(), UserUtil.updateFromTo(new User(user), updatedTo));
    }

    @Test
    void registerInvalid() throws Exception {
        UserTo newTo = new UserTo(null, null, null, null, null, null,
                null, null, null, null, null, false, false, true);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFromObject(newTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_EMAIL)
    void updateInvalid() throws Exception {
        UserTo updatedTo = new UserTo(null, null, "password", null, null, null,
                null, null, null, null, null, false, false, true);
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFromObject(updatedTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_EMAIL)
    void updateDuplicateEmail() throws Exception {
        User updated = getUpdated();
        updated.setEmail(ADMIN_EMAIL);
        UserTo updatedTo = UserUtil.createToFromUser(updated);
        perform(MockMvcRequestBuilders.put(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(updatedTo, "newPassword")))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_EMAIL)
    void updateDuplicatePhone() throws Exception {
        User updated = getUpdated();
        updated.setEmail(ADMIN_PHONE);
        UserTo updatedTo = UserUtil.createToFromUser(updated);
        perform(MockMvcRequestBuilders.put(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(updatedTo, "newPassword")))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_EMAIL)
    void updateInvalidPhone() throws Exception {
        User updated = getUpdated();
        updated.getInfo().setPhone("8999-123-4455");
        UserTo updatedTo = UserUtil.createToFromUser(updated);
        perform(MockMvcRequestBuilders.put(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(updatedTo, "newPassword")))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_EMAIL)
    void updateInvalidZipcode() throws Exception {
        User updated = getUpdated();
        updated.getInfo().getAddress().setZipcode("123-456");
        UserTo updatedTo = UserUtil.createToFromUser(updated);
        perform(MockMvcRequestBuilders.put(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(updatedTo, "newPassword")))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}