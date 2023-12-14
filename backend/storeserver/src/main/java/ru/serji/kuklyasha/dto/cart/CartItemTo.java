package ru.serji.kuklyasha.dto.cart;

import lombok.*;
import org.hibernate.validator.constraints.*;
import ru.serji.kuklyasha.dto.*;

import javax.validation.constraints.*;

@Value
@EqualsAndHashCode(callSuper = true)
public class CartItemTo extends BaseTo {

    @NotNull
    DollTo doll;

    @NotNull
    @Range(min = 1)
    int quantity;

    public CartItemTo(Integer id, DollTo doll, int quantity) {
        super(id);
        this.doll = doll;
        this.quantity = quantity;
    }
}
