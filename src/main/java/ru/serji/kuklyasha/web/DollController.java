package ru.serji.kuklyasha.web;

import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.*;
import ru.serji.kuklyasha.model.*;
import ru.serji.kuklyasha.service.*;

import javax.validation.*;
import java.net.*;
import java.util.*;

import static ru.serji.kuklyasha.util.ValidationUtil.*;

@RestController
@RequestMapping(value = DollController.API_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@CrossOrigin
public class DollController {
    static final String API_URL = "/api/dolls";

    private final DollService dollService;

    @Autowired
    public DollController(DollService dollService) {
        this.dollService = dollService;
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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Doll> create(@Valid @RequestBody Doll doll) {
        log.info("create doll = {}", doll);
        Objects.requireNonNull(doll, "doll must not be null");
        checkNew(doll);
        Doll created = dollService.save(doll);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(API_URL + "/{id}")
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
