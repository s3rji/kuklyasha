package ru.serji.kuklyasha.web.doll;

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

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.serji.kuklyasha.DollTestData.NOT_FOUND;
import static ru.serji.kuklyasha.DollTestData.getNew;
import static ru.serji.kuklyasha.DollTestData.getUpdated;
import static ru.serji.kuklyasha.DollTestData.jsonFromObject;
import static ru.serji.kuklyasha.DollTestData.*;
import static ru.serji.kuklyasha.UserTestData.*;
import static ru.serji.kuklyasha.web.doll.UniqueNameValidator.*;
import static ru.serji.kuklyasha.web.util.DollUtil.*;

class DollControllerTest extends AbstractControllerTest {

    private static final String REST_URL = DollController.REST_URL + "/";

    @Autowired
    private DollService dollService;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + DOLL_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DOLL_TO_MATCHER.contentJson(createToFromDoll(doll)));
    }

    @Test
    void notFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + NOT_FOUND))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DOLL_TO_MATCHER.contentJson(allDolls.stream().map(DollUtil::createToFromDoll).toList()));
    }

    @Test
    void getLimitByPage() throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.get(REST_URL)
                .param("page", "0")
                .param("limit", "3"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        DollPage actual = JsonUtil.readValue(action.andReturn().getResponse().getContentAsString(), DollPage.class);
        DOLL_TO_MATCHER.assertMatch(actual.getContent(), allDolls.subList(0, 3).stream().map(DollUtil::createToFromDoll).toList());
        assertThat(actual.getTotal()).isEqualTo(allDolls.size());
    }

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    void create() throws Exception {
        Doll newDoll = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFromObject(createToFromDoll(newDoll))))
                .andDo(print())
                .andExpect(status().isCreated());

        DollTo created = DOLL_TO_MATCHER.readFromJson(action);
        int newId = created.id();
        newDoll.setId(newId);
        DOLL_TO_MATCHER.assertMatch(created, createToFromDoll(newDoll));
        DOLL_MATCHER.assertMatch(dollService.get(newId).get(), newDoll);
    }

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    void createInvalid() throws Exception {
        Doll invalid = getNew();
        invalid.setName("");
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFromObject(createToFromDoll(invalid))))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    void createDuplicate() throws Exception {
        Doll duplicate = getNew();
        duplicate.setName("Doll2");
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFromObject(createToFromDoll(duplicate))))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString(EXCEPTION_DUPLICATE_NAME)));
    }

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    void update() throws Exception {
        Doll updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + DOLL_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFromObject(createToFromDoll(updated))))
                .andDo(print())
                .andExpect(status().isNoContent());

        DOLL_MATCHER.assertMatch(dollService.get(DOLL_ID).get(), getUpdated());
    }

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    void updateInvalid() throws Exception {
        Doll invalid = getUpdated();
        invalid.setName("");
        perform(MockMvcRequestBuilders.put(REST_URL + DOLL_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFromObject(createToFromDoll(invalid))))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_EMAIL)
    void updateDuplicate() throws Exception {
        Doll invalid = getUpdated();
        invalid.setName("Doll2");
        perform(MockMvcRequestBuilders.put(REST_URL + DOLL_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFromObject(createToFromDoll(invalid))))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString(EXCEPTION_DUPLICATE_NAME)));
    }
}