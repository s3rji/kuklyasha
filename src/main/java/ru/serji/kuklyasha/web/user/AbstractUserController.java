package ru.serji.kuklyasha.web.user;

import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.*;
import ru.serji.kuklyasha.model.*;
import ru.serji.kuklyasha.service.*;
import ru.serji.kuklyasha.util.*;

@Slf4j
public abstract class AbstractUserController {

    protected final UserService userService;

    private final UniqueMailValidator emailValidator;

    public AbstractUserController(UserService userService, UniqueMailValidator emailValidator) {
        this.userService = userService;
        this.emailValidator = emailValidator;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(emailValidator);
    }

    public ResponseEntity<User> get(int id) {
        log.info("get {}", id);
        return ResponseEntity.of(userService.get(id));
    }

    public void delete(int id) {
        log.info("delete {}", id);
        userService.delete(id);
    }

    protected User prepareAndSave(User user) {
        return userService.save(UserUtil.prepareToSave(user));
    }
}