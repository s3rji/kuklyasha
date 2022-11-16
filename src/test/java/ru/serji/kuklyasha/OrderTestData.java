package ru.serji.kuklyasha;

import ru.serji.kuklyasha.dto.*;
import ru.serji.kuklyasha.model.*;
import ru.serji.kuklyasha.web.util.*;

import java.math.*;
import java.time.*;
import java.util.*;

public class OrderTestData {

    public static final MatcherFactory.Matcher<Order> ORDER_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Order.class, "user", "created", "items.created");

    public static final MatcherFactory.Matcher<OrderTo> ORDER_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(OrderTo.class, "items.created");

    public static final int ORDER_ID = 1;

    public static final int NOT_FOUND = 1000;

    public static final Order order = new Order(1, UserTestData.user, Set.copyOf(DollTestData.allDolls),
            new Status(StatusType.NEW, LocalDateTime.of(2022, 10, 31, 0, 0)), new BigDecimal("500.00"));

    public static final Order order1 = new Order(2, UserTestData.user, Set.of(DollTestData.doll),
            new Status(StatusType.DONE, LocalDateTime.of(2022, 10, 31, 0, 0)), new BigDecimal("100.00"));

    public static Order getNew() {
        return new Order(null, UserTestData.user, Set.of(DollTestData.doll),
                new Status(StatusType.NEW, LocalDateTime.of(2022, 11, 1, 0, 0)), new BigDecimal("100.00"));
    }

    public static Order getUpdated() {
        return new Order(1, UserTestData.user, Set.copyOf(DollTestData.allDolls),
                new Status(StatusType.DONE, LocalDateTime.of(2022, 11, 1, 0, 0)), new BigDecimal("500.00"));
    }

    public static String jsonFromObject(OrderTo orderTo) {
        return JsonUtil.writeValue(orderTo);
    }
}
