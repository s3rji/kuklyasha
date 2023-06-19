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

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static OrderTo createToFromOrder(@NonNull Order order) {
        String deliveryDate = order.getDeliveryDate().format(FORMATTER);
        List<PurchasedItemTo> items = order.getItems()
                .stream()
                .map(item -> new PurchasedItemTo(item.getId(), DollUtil.createToFromDoll(item.getDoll()), item.getQuantity(), item.getPrice()))
                .sorted(Comparator.comparingInt(BaseTo::getId))
                .toList();
        return new OrderTo(order.getId(), items, order.getStatus(), order.getTotal(), deliveryDate);
    }

    public static AdminOrderTo createAdminToFromOrder(@NonNull Order order) {
        String deliveryDate = order.getDeliveryDate().format(FORMATTER);
        UserTo user = UserUtil.createToFromUser(order.getUser());
        List<PurchasedItemTo> items = order.getItems()
                .stream()
                .map(item -> new PurchasedItemTo(item.getId(), DollUtil.createToFromDoll(item.getDoll()), item.getQuantity(), item.getPrice()))
                .sorted(Comparator.comparingInt(BaseTo::getId))
                .toList();
        return new AdminOrderTo(order.getId(), user, items, order.getStatus(), order.getTotal(), deliveryDate);
    }
}