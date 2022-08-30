package ru.serji.kuklyasha.web.doll;

import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.transaction.annotation.*;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.*;
import ru.serji.kuklyasha.model.*;
import ru.serji.kuklyasha.service.*;

import javax.validation.*;
import java.net.*;
import java.util.*;

import static ru.serji.kuklyasha.service.util.ValidationUtil.*;

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
    public ResponseEntity<Doll> get(@PathVariable int id) {
        log.info("get doll {}", id);
        return ResponseEntity.of(dollService.get(id));
    }

    @GetMapping
    public List<Doll> getAll() {
        log.info("get all dolls");
        return dollService.getAll();
    }

    @GetMapping(params = {"page", "limit"})
    @Transactional
    public Map<String, Object> getLimitByPage(@RequestParam int page, @RequestParam int limit) {
        log.info("get dolls by page {} and limit {}", page, limit);
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("dolls", dollService.getLimitByPage(page, limit));
        body.put("total", dollService.totalCount());
        return body;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Doll> create(@Valid @RequestBody Doll doll) {
        log.info("create doll = {}", doll);
        Objects.requireNonNull(doll, "doll must not be null");
        checkNew(doll);
        Doll created = dollService.save(doll);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Doll doll, @PathVariable("id") int id) {
        log.info("update doll = {}", doll);
        Objects.requireNonNull(doll, "doll must not be null");
        assureIdConsistent(doll, id);
        dollService.save(doll);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        log.info("delete doll with id = {}", id);
        dollService.delete(id);
    }
}