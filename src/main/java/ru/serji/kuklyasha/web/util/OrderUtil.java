package ru.serji.kuklyasha.web.util;

import lombok.experimental.*;
import org.springframework.lang.*;
import ru.serji.kuklyasha.dto.*;
import ru.serji.kuklyasha.model.*;

@UtilityClass
public class OrderUtil {

    public static Order createOrderFromTo(@NonNull OrderTo orderTo, @NonNull User user) {
        return new Order(orderTo.getId(), user, orderTo.getItems(), orderTo.getStatus(), orderTo.getTotal());
    }

    public static OrderTo createToFromOrder(@NonNull Order order) {
        return new OrderTo(order.getId(), order.getItems(), order.getStatus(), order.getTotal());
    }
}