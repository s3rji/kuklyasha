package ru.serji.kuklyasha.web.admin;

import lombok.extern.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.transaction.annotation.*;
import org.springframework.web.bind.annotation.*;
import ru.serji.kuklyasha.config.*;
import ru.serji.kuklyasha.dto.*;
import ru.serji.kuklyasha.dto.order.*;
import ru.serji.kuklyasha.error.*;
import ru.serji.kuklyasha.events.*;
import ru.serji.kuklyasha.service.*;
import ru.serji.kuklyasha.web.util.*;

import javax.validation.*;
import java.util.*;

import static ru.serji.kuklyasha.service.util.ValidationUtil.*;
import static ru.serji.kuklyasha.web.util.OrderUtil.*;
import static ru.serji.kuklyasha.web.util.UserUtil.*;

@RestController
@RequestMapping(value = AdminController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Log4j2
@Transactional(readOnly = true)
@CrossOrigin(origins = ReactAppProperties.HOST_NAME)
public class AdminController {

    private final static String ACTION_UPDATE = "UPDATE";

    final static String REST_URL = "/api/admin";

    private final OrderService orderService;
    private final UserService userService;

    private final NotificationSourceBean notification;

    @Autowired
    public AdminController(OrderService orderService, UserService userService, NotificationSourceBean notification) {
        this.orderService = orderService;
        this.userService = userService;
        this.notification = notification;
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<AdminOrderTo> getOrder(@PathVariable("id") int id) {
        log.info("get order with id = {} fetching user", id);
        return orderService.getById(id)
                .map(order -> ResponseEntity.ok(createAdminToFromOrder(order)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/orders")
    public List<AdminOrderTo> getAllOrders() {
        log.info("get all orders fetching user");
        return orderService.getAllFetchUser().stream()
                .map(OrderUtil::createAdminToFromOrder).sorted(Comparator.comparingInt(BaseTo::getId)).toList();
    }

    @GetMapping(value = "/orders", params = {"page", "limit", "sort", "direction"})
    public ResponseEntity<AdminOrderPage> getLimitOrdersByPageAndSort(@RequestParam int page, @RequestParam int limit, @RequestParam String sort, @RequestParam String direction) {
        log.info("get limit orders by page {} and limit {} with user", page, limit);
        if (OrderUtil.isNotValidSortField(sort, direction)) {
            throw new IllegalRequestDataException("Sort must be: 'id', 'user.name' or 'status'. Direction must be: 'asc' or 'desc'");
        }

        List<AdminOrderTo> content = orderService.getLimitFetchUserAndSort(page, limit, sort, direction)
                .stream()
                .map(OrderUtil::createAdminToFromOrder)
                .toList();
        int total = orderService.totalCount();
        return ResponseEntity.ok(new AdminOrderPage(content, total));
    }

    @GetMapping(value = "/orders", params = {"page", "limit", "sort", "direction", "field", "filter"})
    public ResponseEntity<AdminOrderPage> getLimitOrdersByFilterAndSort(
            @RequestParam int page, @RequestParam int limit,
            @RequestParam String sort, @RequestParam String direction,
            @RequestParam String field, @RequestParam String filter
    ) {
        log.info("get limit orders with user filtered by field");
        if (OrderUtil.isNotValidSortField(sort, direction)) {
            throw new IllegalRequestDataException("Sort must be: 'id', 'user.name' or 'status'. Direction must be: 'asc' or 'desc'");
        }
        if (OrderUtil.isNotValidFilterField(field, filter)) {
            throw new IllegalRequestDataException("Field must be: 'id', 'user.name' or 'status'. 'Filter' must not be null or empty");
        }

        AdminOrderPage orderPage = createAdminOrderPageFromFilteredOrderPage(
                orderService.getLimitByFilterFetchUserAndSort(page, limit, sort, direction, field, filter)
        );
        return ResponseEntity.ok(orderPage);
    }

    @PatchMapping(value = "/orders/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void updateOrder(@Valid @RequestBody OrderChangeTo orderChange, @PathVariable("id") int id) {
        log.info("update order with id = {}", id);
        Objects.requireNonNull(orderChange, "order change must not be null");
        assureIdConsistent(orderChange, id);
        orderService.getById(id)
                .ifPresent(order -> {
                    orderService.update(updateOrderFromOrderChange(order, orderChange));
                    notification.publishNotification(ACTION_UPDATE, order.getUser().getName(), order.getUser().getEmail(),
                            order.id(), order.getCreated(), order.getStatus().getType().toString());
                });
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserTo> getUser(@PathVariable("id") int id) {
        log.info("get user with id = {}", id);
        return userService.get(id)
                .map(user -> ResponseEntity.ok(createToFromUser(user)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/users/{id}/orders")
    public List<AdminOrderTo> getUserOrders(@PathVariable("id") int id) {
        log.info("get user orders with id = {}", id);
        return userService.get(id)
                .stream()
                .map(orderService::getAllByUser)
                .flatMap(Collection::stream)
                .map(OrderUtil::createAdminToFromOrder)
                .toList();
    }

    @GetMapping("/users")
    public List<UserTo> getAllUsers() {
        log.info("get all users");
        return userService.getAll().stream()
                .map(UserUtil::createToFromUser).sorted(Comparator.comparingInt(BaseTo::getId)).toList();
    }

    @GetMapping(value = "/users", params = {"page", "limit"})
    public ResponseEntity<UserPage> getLimitUsersByPage(@RequestParam int page, @RequestParam int limit) {
        log.info("get limit orders by page {} and limit {}", page, limit);
        List<UserTo> content = userService.getLimitByPage(page, limit).stream().map(UserUtil::createToFromUser).toList();
        int total = userService.totalCount();
        UserPage body = createUserPage(content, total);
        return ResponseEntity.ok(body);
    }

    @PatchMapping(value = "/users/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void updateUser(@RequestBody UserChangeTo userChange, @PathVariable("id") int id) {
        log.info("update user with id = {}", id);
        Objects.requireNonNull(userChange, "user change must not be null");
        assureIdConsistent(userChange, id);
        userService.get(id).ifPresent(user -> user.setEnabled(userChange.isEnabled()));
    }
}
