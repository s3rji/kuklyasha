package ru.serji.kuklyasha.web.order;

import lombok.extern.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
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
import static ru.serji.kuklyasha.web.util.OrderUtil.*;

@RestController
@RequestMapping(value = OrderController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Log4j2
public class OrderController {

    final static String REST_URL = "/api/orders";

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> get(@PathVariable("id") int id) {
        User user = SecurityUtil.authUser();
        log.info("get order {} by user {}", id, user.id());
        return ResponseEntity.of(orderService.get(id, user));
    }

    @GetMapping
    public List<Order> getAllByUser() {
        User user = SecurityUtil.authUser();
        log.info("get all by user {}", user.id());
        return orderService.getAll(user);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> create(@Valid @RequestBody OrderTo orderTo) {
        log.info("create order = {}", orderTo);
        Objects.requireNonNull(orderTo, "order must not be null");
        checkNew(orderTo);
        User user = SecurityUtil.authUser();
        Order created = createOrderFromTo(orderTo, user);
        orderService.save(created, user);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody OrderTo orderTo, @PathVariable("id") int id) {
        log.info("update order = {}", orderTo);
        Objects.requireNonNull(orderTo, "order must not be null");
        assureIdConsistent(orderTo, id);
        User user = SecurityUtil.authUser();
        orderService.save(createOrderFromTo(orderTo, user), user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        log.info("delete order with id = {}", id);
        User user = SecurityUtil.authUser();
        orderService.delete(id, user);
    }
}