package ru.serji.kuklyasha;

import ru.serji.kuklyasha.dto.order.*;
import ru.serji.kuklyasha.model.*;
import ru.serji.kuklyasha.web.util.*;

import java.math.*;
import java.time.*;
import java.util.*;

import static ru.serji.kuklyasha.DollTestData.*;
import static ru.serji.kuklyasha.PurchasedItemTestData.*;

public class OrderTestData {

    public static final MatcherFactory.Matcher<Order> ORDER_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Order.class,
            "user", "items.user", "items.doll.created", "status.modified", "created");

    public static final MatcherFactory.Matcher<OrderTo> ORDER_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(OrderTo.class, "items.doll.created", "status.modified");

    public static final int ORDER_ID = 1;

    public static final int NOT_FOUND = 1000;

    public static final Order order = new Order(ORDER_ID, UserTestData.user, Set.of(item, item2),
            new Status(StatusType.NEW, LocalDateTime.of(2022, 10, 31, 0, 0)), new BigDecimal("200.00"));

    public static final Order order1 = new Order(ORDER_ID + 1, UserTestData.user, Set.of(item3),
            new Status(StatusType.DONE, LocalDateTime.of(2022, 10, 31, 0, 0)), new BigDecimal("100.00"));

    public static Order getNew() {
        return new Order(null, UserTestData.user, Set.of(PurchasedItemTestData.getNew()),
                new Status(StatusType.NEW, LocalDateTime.of(2022, 11, 1, 0, 0)), new BigDecimal("100.00"));
    }

    public static Order getInvalidNew() {
        return new Order(null, UserTestData.user, Set.of(PurchasedItemTestData.getInvalidQuantity()),
                new Status(StatusType.NEW, LocalDateTime.of(2022, 11, 1, 0, 0)), new BigDecimal("10000.00"));
    }

    public static List<PurchasedDoll> getNewPurchasedDolls() {
        List<PurchasedDoll> list = new ArrayList<>();
        list.add(new PurchasedDoll(1, 1));
        return list;
    }

    public static Order getAfterCreating() {
        Doll dollAfterCreatingOrder = new Doll(DOLL_ID, "Doll1", "Pretty Doll", new BigDecimal("100.00"), 1, "/image1");
        PurchasedItem itemAfterCreatingOrder = new PurchasedItem(null, dollAfterCreatingOrder, UserTestData.user, 1, new BigDecimal("100.00"));
        return new Order(null, UserTestData.user, Set.of(itemAfterCreatingOrder),
                new Status(StatusType.NEW, LocalDateTime.of(2022, 11, 1, 0, 0)), new BigDecimal("100.00"));
    }

    public static Order getUpdated() {
        return new Order(ORDER_ID, UserTestData.user, Set.of(item, item2),
                new Status(StatusType.DONE, LocalDateTime.of(2022, 11, 1, 0, 0)), new BigDecimal("200.00"));
    }

    public static String jsonFromObject(OrderTo orderTo) {
        return JsonUtil.writeValue(orderTo);
    }
}
