package ru.serji.kuklyasha.dto.order;

import lombok.*;
import org.hibernate.validator.constraints.*;
import ru.serji.kuklyasha.dto.*;

import javax.validation.constraints.*;
import java.math.*;

@Value
@EqualsAndHashCode(callSuper = true)
public class PurchasedItemTo extends BaseTo {

    @NotNull
    DollTo doll;

    @NotNull
    @Range(min = 1)
    int quantity;

    @NotNull
    @Range(min = 1, max = 100000)
    @Digits(integer = 6, fraction = 2)
    BigDecimal price;

    public PurchasedItemTo(Integer id, DollTo doll, int quantity, BigDecimal price) {
        super(id);
        this.doll = doll;
        this.quantity = quantity;
        this.price = price;
    }
}
