package ru.serji.kuklyasha.web.user;

import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.core.annotation.*;
import org.springframework.transaction.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.*;
import ru.serji.kuklyasha.dto.*;
import ru.serji.kuklyasha.model.*;
import ru.serji.kuklyasha.service.*;
import ru.serji.kuklyasha.web.*;

import javax.validation.*;
import java.net.*;

import static ru.serji.kuklyasha.service.util.ValidationUtil.*;
import static ru.serji.kuklyasha.web.util.UserUtil.*;

@RestController
@RequestMapping(value = ProfileController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
public class ProfileController extends AbstractUserController {

    static final String REST_URL = "/api/profile";

    @Autowired
    public ProfileController(UserService userService, UniqueMailValidator emailValidator) {
        super(userService, emailValidator);
    }

    @GetMapping
    public UserTo get(@AuthenticationPrincipal AuthUser authUser) {
        User user = authUser.getUser();
        return createToFromUser(user);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthUser authUser) {
        super.delete(authUser.id());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public ResponseEntity<UserTo> register(@Valid @RequestBody UserTo userTo) {
        log.info("register {}", userTo);
        checkNew(userTo);
        User created = prepareAndSave(createNewFromTo(userTo));

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL)
                .build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(createToFromUser(created));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody @Valid UserTo userTo, @AuthenticationPrincipal AuthUser authUser) {
        assureIdConsistent(userTo, authUser.id());
        User user = authUser.getUser();
        prepareAndSave(updateFromTo(user, userTo));
    }
}