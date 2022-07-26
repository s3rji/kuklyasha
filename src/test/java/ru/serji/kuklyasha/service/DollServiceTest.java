package ru.serji.kuklyasha.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.dao.*;
import org.springframework.transaction.annotation.*;
import ru.serji.kuklyasha.error.*;
import ru.serji.kuklyasha.model.*;

import javax.validation.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static ru.serji.kuklyasha.DollTestData.*;

@SpringBootTest
@Transactional
class DollServiceTest {

    @Autowired
    private DollService dollService;

    @Test
    void get() {
        Doll actual = dollService.get(DOLL_ID).get();
        DOLL_MATCHER.assertMatch(actual, doll);
    }

    @Test
    void notFound() {
        assertTrue(dollService.get(NOT_FOUND).isEmpty());
    }

    @Test
    void getAll() {
        List<Doll> actual = dollService.getAll();
        DOLL_MATCHER.assertMatch(actual, allDolls);
    }

    @Test
    void create() {
        Doll created = dollService.save(getNew());
        int newId = created.id();
        Doll newDoll = getNew();
        newDoll.setId(newId);
        DOLL_MATCHER.assertMatch(created, newDoll);
        DOLL_MATCHER.assertMatch(dollService.get(newId).get(), newDoll);
    }

    @Test
    void createInvalid() {
        Doll newDoll = getNew();
        newDoll.setName("");
        assertThrows(ConstraintViolationException.class, () -> dollService.save(newDoll));
    }

    @Test
    void update() {
        Doll updated = getUpdated();
        dollService.save(updated);
        DOLL_MATCHER.assertMatch(dollService.get(DOLL_ID).get(), getUpdated());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void duplicateNameUpdate() {
        Doll duplicateUpdated = getUpdated();
        duplicateUpdated.setName("Doll2");
        assertThrows(DataAccessException.class, () -> dollService.save(duplicateUpdated));
    }

    @Test
    void delete() {
        dollService.delete(DOLL_ID);
        assertThrows(IllegalRequestDataException.class, () -> dollService.delete(DOLL_ID));
        assertTrue(dollService.get(DOLL_ID).isEmpty());
    }

    @Test
    void deleteNotFound() {
        assertThrows(IllegalRequestDataException.class, () -> dollService.delete(NOT_FOUND));
    }
}