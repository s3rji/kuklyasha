package ru.serji.kuklyasha.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.serji.kuklyasha.model.Doll;
import ru.serji.kuklyasha.service.DollService;

import java.util.List;
import java.util.Objects;

import static ru.serji.kuklyasha.util.ValidationUtil.assureIdConsistent;
import static ru.serji.kuklyasha.util.ValidationUtil.checkNew;

@Controller
@RequestMapping("/dolls")
@Slf4j
public class DollController {
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

    @PostMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void create(Doll doll) {
        log.info("create doll = {}", doll);
        Objects.requireNonNull(doll, "meal must not be null");
        checkNew(doll);
        dollService.save(doll);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(Doll doll, @PathVariable("id") int id) {
        log.info("update doll = {}", doll);
        Objects.requireNonNull(doll, "meal must not be null");
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
