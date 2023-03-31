package ru.serji.kuklyasha;

import ru.serji.kuklyasha.model.*;

import java.math.*;

import static ru.serji.kuklyasha.DollTestData.*;
import static ru.serji.kuklyasha.UserTestData.*;

public class PurchasedItemTestData {
    public static final MatcherFactory.Matcher<PurchasedItem> PURCHASED_ITEM_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(PurchasedItem.class,
            "user", "doll.created");

    public static final int PURCHASED_ITEM_ID = 1;

    public static final PurchasedItem item = new PurchasedItem(PURCHASED_ITEM_ID, new Doll(DOLL_ID, "Doll1", "Pretty Doll", new BigDecimal("100.00"), 2, "/image1"), user, 1, new BigDecimal("100.00"));

    public static final PurchasedItem item2 = new PurchasedItem(PURCHASED_ITEM_ID + 1, new Doll(DOLL_ID + 1, "Doll2", "Pretty Doll", new BigDecimal("100.00"), 1, "/image2"), user, 1, new BigDecimal("100.00"));

    public static final PurchasedItem item3 = new PurchasedItem(PURCHASED_ITEM_ID + 2, new Doll(DOLL_ID + 2, "Doll3", "Pretty Doll", new BigDecimal("100.00"), 1, "/image3"), user, 2, new BigDecimal("100.00"));

    public static PurchasedItem getNew() {
        return new PurchasedItem(null, new Doll(DOLL_ID, "Doll1", "Pretty Doll", new BigDecimal("100.00"), 2, "/image1"), user, 1, new BigDecimal("100.00"));
    }

    public static PurchasedItem getInvalidQuantity() {
        return new PurchasedItem(null, doll, user, 100, new BigDecimal("0.00"));
    }

    public static PurchasedItem getInvalidNew() {
        return new PurchasedItem(null, doll, user, 0, new BigDecimal("0.00"));
    }
}