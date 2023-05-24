package ru.serji.kuklyasha.web.doll;

import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.transaction.annotation.*;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.*;
import ru.serji.kuklyasha.dto.*;
import ru.serji.kuklyasha.model.*;
import ru.serji.kuklyasha.service.*;
import ru.serji.kuklyasha.web.util.*;

import javax.validation.*;
import java.net.*;
import java.util.*;

import static ru.serji.kuklyasha.service.util.ValidationUtil.*;
import static ru.serji.kuklyasha.web.util.DollUtil.*;

@RestController
@RequestMapping(value = DollController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
public class DollController {

    static final String REST_URL = "/api/dolls";

    private final DollService dollService;

    private final UniqueNameValidator validator;

    @Autowired
    public DollController(DollService dollService, UniqueNameValidator validator) {
        this.dollService = dollService;
        this.validator = validator;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<DollTo> get(@PathVariable("id") int id) {
        log.info("get doll {}", id);
        return dollService.get(id)
                .map(doll -> ResponseEntity.ok(createToFromDoll(doll)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<DollTo> getAll() {
        log.info("get all dolls");
        return dollService.getAll().stream()
                .map(DollUtil::createToFromDoll).sorted(Comparator.comparingInt(BaseTo::getId)).toList();
    }

    @GetMapping(params = {"page", "limit"})
    @Transactional(readOnly = true)
    public ResponseEntity<DollPage> getLimitByPage(@RequestParam int page, @RequestParam int limit) {
        log.info("get dolls by page {} and limit {}", page, limit);
        List<DollTo> content = dollService.getLimitByPage(page, limit).stream().map(DollUtil::createToFromDoll).toList();
        int total = dollService.totalCount();
        DollPage body = createDollPage(content, total);
        return ResponseEntity.ok(body);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DollTo> create(@Valid @RequestBody DollTo dollTo) {
        log.info("create doll = {}", dollTo);
        Objects.requireNonNull(dollTo, "doll must not be null");
        checkNew(dollTo);
        Doll created = dollService.save(createDollFromTo(dollTo));

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(createToFromDoll(created));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody DollTo dollTo, @PathVariable("id") int id) {
        log.info("update doll = {}", dollTo);
        Objects.requireNonNull(dollTo, "doll must not be null");
        assureIdConsistent(dollTo, id);
        dollService.save(createDollFromTo(dollTo));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        log.info("delete doll with id = {}", id);
        dollService.delete(id);
    }
}