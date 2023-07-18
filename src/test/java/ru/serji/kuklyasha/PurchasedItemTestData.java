package ru.serji.kuklyasha;

import ru.serji.kuklyasha.model.*;

import java.math.*;
import java.util.*;

import static ru.serji.kuklyasha.DollTestData.*;
import static ru.serji.kuklyasha.OrderTestData.*;
import static ru.serji.kuklyasha.UserTestData.*;

public class PurchasedItemTestData {

    public static final int PURCHASED_ITEM_ID = 1;

    public static final Set<String> gallery = new HashSet<>();

    static {
        gallery.add("/image1");
        gallery.add("/image2");
        gallery.add("/image3");
        gallery.add("/image4");
    }

    public static final PurchasedItem item = new PurchasedItem(PURCHASED_ITEM_ID, order, new Doll(DOLL_ID, "Doll1", "Pretty Doll", new BigDecimal("100.00"), 2, "/image1", gallery), user, 1, new BigDecimal("100.00"));

    public static final PurchasedItem item2 = new PurchasedItem(PURCHASED_ITEM_ID + 1, order, new Doll(DOLL_ID + 1, "Doll2", "Pretty Doll", new BigDecimal("100.00"), 1, "/image2", new HashSet<>()), user, 1, new BigDecimal("100.00"));

    public static final PurchasedItem item3 = new PurchasedItem(PURCHASED_ITEM_ID + 2, order1, new Doll(DOLL_ID + 2, "Doll3", "Pretty Doll", new BigDecimal("100.00"), 1, "/image3", new HashSet<>()), user, 2, new BigDecimal("100.00"));

    public static final PurchasedItem item4 = new PurchasedItem(PURCHASED_ITEM_ID + 3, order2, new Doll(DOLL_ID + 2, "Doll3", "Pretty Doll", new BigDecimal("100.00"), 1, "/image3", new HashSet<>()), admin, 2, new BigDecimal("100.00"));
}