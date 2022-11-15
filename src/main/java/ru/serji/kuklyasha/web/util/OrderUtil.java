package ru.serji.kuklyasha.web.util;

import lombok.experimental.*;
import ru.serji.kuklyasha.dto.*;
import ru.serji.kuklyasha.model.*;

@UtilityClass
public class OrderUtil {

    public static Order createOrderFromTo(OrderTo orderTo, User user) {
        return new Order(orderTo.getId(), user, orderTo.getItems(), orderTo.getStatus(), orderTo.getTotal());
    }
}