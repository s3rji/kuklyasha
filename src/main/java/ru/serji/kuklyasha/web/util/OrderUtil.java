package ru.serji.kuklyasha.web.util;

import lombok.experimental.*;
import org.springframework.lang.*;
import ru.serji.kuklyasha.dto.*;
import ru.serji.kuklyasha.model.*;

import java.time.format.*;

@UtilityClass
public class OrderUtil {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static Order createOrderFromTo(@NonNull OrderTo orderTo, @NonNull User user) {
        return new Order(orderTo.getId(), user, orderTo.getItems(), orderTo.getStatus(), orderTo.getTotal());
    }

    public static OrderTo createToFromOrder(@NonNull Order order) {
        String deliveryDate = order.getCreated().format(FORMATTER) + " - " + order.getCreated().plusDays(4).format(FORMATTER);
        return new OrderTo(order.getId(), order.getItems(), order.getStatus(), order.getTotal(), deliveryDate);
    }
}