package ru.serji.kuklyasha.dto;

import lombok.*;
import org.hibernate.validator.constraints.*;

import javax.validation.constraints.*;

@Value
@EqualsAndHashCode(callSuper = true)
public class PurchasedDoll extends BaseTo {

    @NotNull
    @Range(min = 1)
    int quantity;

    public PurchasedDoll(Integer id, int quantity) {
        super(id);
        this.quantity = quantity;
    }
}
