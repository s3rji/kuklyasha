package ru.serji.kuklyasha.web.order;

import lombok.extern.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.transaction.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.*;
import ru.serji.kuklyasha.dto.*;
import ru.serji.kuklyasha.dto.order.*;
import ru.serji.kuklyasha.error.*;
import ru.serji.kuklyasha.events.*;
import ru.serji.kuklyasha.model.*;
import ru.serji.kuklyasha.service.*;
import ru.serji.kuklyasha.web.util.*;

import javax.validation.*;
import javax.validation.constraints.*;
import java.math.*;
import java.net.*;
import java.util.*;

import static ru.serji.kuklyasha.web.util.OrderUtil.*;

@RestController
@RequestMapping(value = OrderController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Log4j2
@Transactional(readOnly = true)
//@CrossOrigin(origins = ReactAppProperties.HOST_NAME)
public class OrderController {

    private final static String ACTION_CREATE = "CREATE";

    final static String REST_URL = "/api/orders";

    private final OrderService orderService;
    private final DollService dollService;

    private final NotificationSourceBean notification;

    @Autowired
    public OrderController(OrderService orderService, DollService dollService, NotificationSourceBean notification) {
        this.orderService = orderService;
        this.dollService = dollService;
        this.notification = notification;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderTo> get(@PathVariable("id") int id) {
        User user = SecurityUtil.authUser();
        log.info("get order {} by user {}", id, user.id());

        return orderService.getByIdAndUser(id, user)
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
        Order newOrder = new Order(null, user, new Status(StatusType.NEW));
        addPurchasedItemsAndSetTotal(newOrder, dolls);
        Order created = orderService.create(newOrder);

        notification.publishNotification(ACTION_CREATE, user.getName(), user.getEmail(), created.id(),
                created.getCreated(), created.getStatus().getType().toString());

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(createToFromOrder(created));
    }

    private void addPurchasedItemsAndSetTotal(Order order, List<PurchasedDoll> dolls) {
        dolls.forEach(purchasedDoll -> dollService.get(purchasedDoll.id()).ifPresent(doll -> {
                    if (purchasedDoll.getQuantity() > doll.getQuantity()) {
                        throw new IllegalRequestDataException("not enough doll quantity with id = " + doll.id() + " in stock");
                    }
                    doll.setQuantity(doll.getQuantity() - purchasedDoll.getQuantity());
                    order.addItem(new PurchasedItem(null, order, doll, order.getUser(), purchasedDoll.getQuantity(), doll.getPrice()));
                })
        );
        BigDecimal total = order.getItems().stream().map(PurchasedItem::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotal(total);
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