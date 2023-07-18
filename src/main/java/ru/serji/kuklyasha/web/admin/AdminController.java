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
import ru.serji.kuklyasha.service.*;
import ru.serji.kuklyasha.web.util.*;

import java.util.*;

@RestController
@RequestMapping(value = AdminController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Log4j2
@Transactional(readOnly = true)
@CrossOrigin(origins = ReactAppProperties.HOST_NAME)
public class AdminController {
    final static String REST_URL = "/api/admin";

    private final OrderService orderService;

    @Autowired
    public AdminController(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping("/orders")
    public List<AdminOrderTo> getAllOrders() {
        log.info("get all orders with user");
        return orderService.getAllFetchUser().stream()
                .map(OrderUtil::createAdminToFromOrder).sorted(Comparator.comparingInt(BaseTo::getId)).toList();
    }

    @GetMapping(value = "/orders", params = {"page", "limit", "sort", "direction"})
    public List<AdminOrderTo> getLimitByPageAndSort(@RequestParam int page, @RequestParam int limit, @RequestParam String sort, @RequestParam String direction) {
        log.info("get limit orders by page with user");
        if (OrderUtil.isNotValidSortField(sort, direction)) {
            throw new IllegalRequestDataException("Sort must be: 'id', 'user.name' or 'status'. Direction must be: 'asc' or 'desc'");
        }
        return orderService.getLimitFetchUserAndSort(page, limit, sort, direction)
                .stream()
                .map(OrderUtil::createAdminToFromOrder)
                .toList();
    }

    @GetMapping(value = "/orders", params = {"page", "limit", "sort", "direction", "field", "filter"})
    public List<AdminOrderTo> getLimitByFilterAndSort(
            @RequestParam int page, @RequestParam int limit,
            @RequestParam String sort, @RequestParam String direction,
            @RequestParam String field, @RequestParam String filter
    ) {
        log.info("get limit orders with user filtered by field");
        Objects.requireNonNull(filter, "filter must not be null");

        if (OrderUtil.isNotValidSortField(sort, direction)) {
            throw new IllegalRequestDataException("Sort must be: 'id', 'user.name' or 'status'. Direction must be: 'asc' or 'desc'");
        }
        if (OrderUtil.isNotValidFilterField(field)) {
            throw new IllegalRequestDataException("Field must be: 'id', 'user.name' or 'status'");
        }

        return orderService.getLimitByFilterFetchUserAndSort(page, limit, sort, direction, field, filter)
                .stream()
                .map(OrderUtil::createAdminToFromOrder)
                .toList();
    }
}
