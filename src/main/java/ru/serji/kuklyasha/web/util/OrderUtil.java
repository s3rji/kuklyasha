package ru.serji.kuklyasha.web.util;

import lombok.experimental.*;
import org.springframework.lang.*;
import ru.serji.kuklyasha.dto.*;
import ru.serji.kuklyasha.model.*;

import java.time.format.*;
import java.util.*;
import java.util.stream.*;

@UtilityClass
public class OrderUtil {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static OrderTo createToFromOrder(@NonNull Order order) {
        String deliveryDate = order.getCreated().format(FORMATTER) + " - " + order.getCreated().plusDays(4).format(FORMATTER);
        List<PurchasedItemTo> items = order.getItems()
                .stream()
                .map(item -> new PurchasedItemTo(item.getId(), item.getDoll(), item.getQuantity(), item.getPrice()))
                .sorted(Comparator.comparingInt(BaseTo::getId))
                .collect(Collectors.toList());
        return new OrderTo(order.getId(), items, order.getStatus(), order.getTotal(), deliveryDate);
    }
}