package ru.serji.kuklyasha.web.util;

import lombok.experimental.*;
import org.springframework.lang.*;
import ru.serji.kuklyasha.dto.*;
import ru.serji.kuklyasha.dto.order.*;
import ru.serji.kuklyasha.model.*;

import java.time.format.*;
import java.util.*;

@UtilityClass
public class OrderUtil {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public static OrderTo createToFromOrder(@NonNull Order order) {
        String deliveryDate = order.getDeliveryDate().format(DATE_FORMATTER);
        List<PurchasedItemTo> items = order.getItems()
                .stream()
                .map(item -> new PurchasedItemTo(item.getId(), DollUtil.createToFromDoll(item.getDoll()), item.getQuantity(), item.getPrice()))
                .sorted(Comparator.comparingInt(BaseTo::getId))
                .toList();
        return new OrderTo(order.getId(), items, order.getStatus(), order.getTotal(), deliveryDate);
    }

    public static AdminOrderTo createAdminToFromOrder(@NonNull Order order) {
        String deliveryDate = order.getDeliveryDate().format(DATE_FORMATTER);
        String created = order.getCreated().format(DATE_TIME_FORMATTER);
        UserTo user = UserUtil.createToFromUser(order.getUser());
        List<PurchasedItemTo> items = order.getItems()
                .stream()
                .map(item -> new PurchasedItemTo(item.getId(), DollUtil.createToFromDoll(item.getDoll()), item.getQuantity(), item.getPrice()))
                .sorted(Comparator.comparingInt(BaseTo::getId))
                .toList();
        return new AdminOrderTo(order.getId(), user, items, order.getStatus(), order.getTotal(), deliveryDate, created);
    }

    public static boolean isNotValidSortField(String sort, String direction) {
        return (!sort.equals("id") && !sort.equals("user.name") && !sort.equals("status"))
                || (!direction.equals("asc") && !direction.equals("desc"));
    }

    public static boolean isNotValidFilterField(String field) {
        return !field.equals("id") && !field.equals("user.name") && !field.equals("status");
    }
}