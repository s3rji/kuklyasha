package ru.serji.kuklyasha.web.order;

import lombok.extern.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.transaction.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.*;
import ru.serji.kuklyasha.dto.*;
import ru.serji.kuklyasha.dto.order.*;
import ru.serji.kuklyasha.model.*;
import ru.serji.kuklyasha.service.*;
import ru.serji.kuklyasha.web.util.*;

import javax.validation.*;
import javax.validation.constraints.*;
import java.net.*;
import java.util.*;

import static ru.serji.kuklyasha.web.util.OrderUtil.*;

@RestController
@RequestMapping(value = OrderController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Log4j2
@Transactional(readOnly = true)
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

    final static String REST_URL = "/api/orders";

    private final OrderService orderService;
    private final DollService dollService;

    @Autowired
    public OrderController(OrderService orderService, DollService dollService) {
        this.orderService = orderService;
        this.dollService = dollService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderTo> get(@PathVariable("id") int id) {
        User user = SecurityUtil.authUser();
        log.info("get order {} by user {}", id, user.id());

        return orderService.get(id, user)
                .map(order -> ResponseEntity.ok(createToFromOrder(order)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<OrderTo> getAllByUser() {
        User user = SecurityUtil.authUser();
        log.info("get all by user {}", user.id());
        return orderService.getAllByUser(user).stream()
                .map(OrderUtil::createToFromOrder).sorted(Comparator.comparingInt(BaseTo::getId)).toList();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<OrderTo> create(@RequestBody @NotEmpty(message = "Purchased doll list cannot be empty.")
                                          List<@Valid PurchasedDoll> dolls) {
        log.info("create order with dolls = {}", dolls);
        Objects.requireNonNull(dolls, "dolls must not be null");
        User user = SecurityUtil.authUser();
        Set<PurchasedItem> items = mapDollToPurchasedItem(dolls, user);
        Order created = orderService.create(items, user);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(createToFromOrder(created));
    }

    private Set<PurchasedItem> mapDollToPurchasedItem(List<PurchasedDoll> dolls, User user) {
        Set<PurchasedItem> set = new HashSet<>();
        dolls.forEach(purchasedDoll -> dollService.get(purchasedDoll.id())
                .ifPresent(doll -> set.add(new PurchasedItem(null, doll, user, purchasedDoll.getQuantity(), doll.getPrice())))
        );
        return set;
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
            orderService.update(order, user);
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