package ru.serji.kuklyasha;

import ru.serji.kuklyasha.dto.*;
import ru.serji.kuklyasha.model.*;
import ru.serji.kuklyasha.web.util.*;

import java.math.*;
import java.util.*;

import static ru.serji.kuklyasha.DollTestData.*;
import static ru.serji.kuklyasha.UserTestData.*;

public class CartItemTestData {
    public static final MatcherFactory.Matcher<CartItem> CART_ITEM_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(CartItem.class,
            "user", "doll.created");

    public static final MatcherFactory.Matcher<CartItemTo> CART_ITEM_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(CartItemTo.class, "doll.created");

    public static final int CART_ITEM_ID = 1;

    public static final int NOT_FOUND = 1000;

    public static final CartItem cartItem = new CartItem(CART_ITEM_ID, new Doll(DOLL_ID, "Doll1", "Pretty Doll", new BigDecimal("100.00"), 2, "/image1"), user, 1);

    public static final List<CartItem> allCartItems = List.of(
            new CartItem(CART_ITEM_ID, new Doll(DOLL_ID, "Doll1", "Pretty Doll", new BigDecimal("100.00"), 2, "/image1"), user, 1),
            new CartItem(CART_ITEM_ID + 1, new Doll(DOLL_ID + 1, "Doll2", "Pretty Doll", new BigDecimal("100.00"), 1, "/image2"), user, 1),
            new CartItem(CART_ITEM_ID + 2, new Doll(DOLL_ID + 2, "Doll3", "Pretty Doll", new BigDecimal("100.00"), 1, "/image3"), user, 2)
    );

    public static CartItem getNew() {
        return new CartItem(null, doll, admin, 1);
    }

    public static CartItem getInvalidNew() {
        return new CartItem(null, doll, user, 1);
    }

    public static CartItem getUpdated() {
        return new CartItem(CART_ITEM_ID, doll, user, 2);
    }

    public static String jsonFromObject(CartItemTo cartItemTo) {
        return JsonUtil.writeValue(cartItemTo);
    }
}