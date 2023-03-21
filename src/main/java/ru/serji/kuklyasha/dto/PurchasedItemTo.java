package ru.serji.kuklyasha.dto;

import lombok.*;
import org.hibernate.validator.constraints.*;
import ru.serji.kuklyasha.model.*;

import javax.validation.constraints.*;

@Value
@EqualsAndHashCode(callSuper = true)
public class PurchasedItemTo extends BaseTo {

    @NotNull
    Doll doll;

    @NotNull
    @Range(min = 1)
    int quantity;

    public PurchasedItemTo(Integer id, Doll doll, int quantity) {
        super(id);
        this.doll = doll;
        this.quantity = quantity;
    }
}
