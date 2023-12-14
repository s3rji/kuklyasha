package ru.serji.kuklyasha;

import ru.serji.kuklyasha.dto.order.*;
import ru.serji.kuklyasha.model.*;

import java.math.*;
import java.time.*;
import java.util.*;

import static ru.serji.kuklyasha.DollTestData.*;
import static ru.serji.kuklyasha.PurchasedItemTestData.*;
import static ru.serji.kuklyasha.UserTestData.*;

public class OrderTestData {

    public static final MatcherFactory.Matcher<Order> ORDER_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Order.class,
            "user", "items.user", "items.order", "items.doll.created", "status.modified", "created");

    public static final MatcherFactory.Matcher<OrderTo> ORDER_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(OrderTo.class, "items.doll.created", "status.modified");

    public static final MatcherFactory.Matcher<AdminOrderTo> ADMIN_ORDER_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(AdminOrderTo.class, "user.password", "items.doll.created", "status.modified", "created");

    public static final int ORDER_ID = 1;

    public static final int NOT_FOUND = 1000;

    public static final Set<String> gallery = new HashSet<>();

    static {
        gallery.add("/image1");
        gallery.add("/image2");
        gallery.add("/image3");
        gallery.add("/image4");
    }

    public static final Order order = new Order(ORDER_ID, UserTestData.user, Set.of(item, item2),
            new Status(StatusType.NEW, LocalDateTime.now()), new BigDecimal("200.00"));

    public static final Order order1 = new Order(ORDER_ID + 1, UserTestData.user, Set.of(item3),
            new Status(StatusType.DONE, LocalDateTime.now()), new BigDecimal("100.00"));

    public static final Order order2 = new Order(ORDER_ID + 2, UserTestData.admin, Set.of(item4),
            new Status(StatusType.DONE, LocalDateTime.now()), new BigDecimal("100.00"));

    public static Order getNew() {
        Order newOrder = new Order(null, UserTestData.user, new Status(StatusType.NEW, LocalDateTime.now()));
        PurchasedItem newItem = new PurchasedItem(null, newOrder, new Doll(DOLL_ID, "Doll1", "Pretty Doll", new BigDecimal("100.00"), 2, "/image1", Set.of("/image2")), user, 1, new BigDecimal("100.00"));
        newOrder.addItem(newItem);
        newOrder.setTotal(new BigDecimal("100.00"));
        return newOrder;
    }

    public static List<PurchasedDoll> getNewPurchasedDolls() {
        List<PurchasedDoll> list = new ArrayList<>();
        list.add(new PurchasedDoll(1, 1));
        return list;
    }

    public static Order getAfterCreating() {
        Order newOrder = new Order(null, UserTestData.user, new Status(StatusType.NEW, LocalDateTime.now()));
        Doll dollAfterCreatingOrder = new Doll(DOLL_ID, "Doll1", "Pretty Doll", new BigDecimal("100.00"), 1, "/image1", gallery);
        PurchasedItem itemAfterCreatingOrder = new PurchasedItem(null, newOrder, dollAfterCreatingOrder, UserTestData.user, 1, new BigDecimal("100.00"));
        newOrder.addItem(itemAfterCreatingOrder);
        newOrder.setTotal(new BigDecimal("100.00"));
        return newOrder;
    }

    public static Order getUpdated() {
        return new Order(ORDER_ID, UserTestData.user, Set.of(item, item2),
                new Status(StatusType.DONE, LocalDateTime.now()), new BigDecimal("200.00"));
    }
}
