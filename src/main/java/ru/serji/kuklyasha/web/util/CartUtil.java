package ru.serji.kuklyasha.web.util;

import lombok.experimental.*;
import org.springframework.lang.*;
import ru.serji.kuklyasha.dto.*;
import ru.serji.kuklyasha.model.*;

@UtilityClass
public class CartUtil {

    public static CartItemTo createToFromCartItem(@NonNull CartItem cartItem) {
        return new CartItemTo(cartItem.getId(), cartItem.getDoll(), cartItem.getQuantity());
    }

    public static CartItem createCartItemFromTo(@NonNull CartItemTo cartItemTo, @NonNull User user) {
        return new CartItem(cartItemTo.getId(), cartItemTo.getDoll(), user, cartItemTo.getQuantity());
    }
}