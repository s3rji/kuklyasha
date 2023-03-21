package ru.serji.kuklyasha.web.order;

import lombok.extern.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.transaction.annotation.*;
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
@Transactional(readOnly = true)
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

    final static String REST_URL = "/api/orders";

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderTo> get(@PathVariable("id") int id) {
        User user = SecurityUtil.authUser();
        log.info("get order {} by user {}", id, user.id());
        Order order = orderService.get(id, user).orElse(null);
        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.of(Optional.of(createToFromOrder(order)));
    }

    @GetMapping
    public List<OrderTo> getAllByUser() {
        User user = SecurityUtil.authUser();
        log.info("get all by user {}", user.id());
        return orderService.getAll(user).stream()
                .map(OrderUtil::createToFromOrder).sorted(Comparator.comparingInt(BaseTo::getId)).toList();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<OrderTo> create(@Valid @RequestBody OrderTo orderTo) {
        log.info("create order = {}", orderTo);
        Objects.requireNonNull(orderTo, "order must not be null");
        checkNew(orderTo);
        User user = SecurityUtil.authUser();
        Order order = createOrderFromTo(orderTo, user);
        Order created = orderService.save(order, user);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(createToFromOrder(created));
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void updateStatus(@Valid @RequestBody StatusTo statusTo, @PathVariable("id") int id) {
        log.info("update order = {}", id);
        Objects.requireNonNull(statusTo, "status must not be null");
        User user = SecurityUtil.authUser();
        orderService.get(id, user).ifPresent(order -> {
            Status status = new Status(statusTo.getType());
            order.setStatus(status);
            orderService.save(order, user);
        });
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void delete(@PathVariable("id") int id) {
        log.info("delete order with id = {}", id);
        User user = SecurityUtil.authUser();
        orderService.delete(id, user);
    }
}