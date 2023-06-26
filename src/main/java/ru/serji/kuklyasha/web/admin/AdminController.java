package ru.serji.kuklyasha.web.admin;

import lombok.extern.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.transaction.annotation.*;
import org.springframework.web.bind.annotation.*;
import ru.serji.kuklyasha.config.*;
import ru.serji.kuklyasha.dto.*;
import ru.serji.kuklyasha.dto.order.*;
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
        return orderService.getLimitFetchUserAndSort(page, limit, sort, direction)
                .stream()
                .map(OrderUtil::createAdminToFromOrder)
                .toList();
    }
}
