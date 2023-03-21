package ru.serji.kuklyasha.dto;

import lombok.*;
import org.hibernate.validator.constraints.*;
import ru.serji.kuklyasha.model.*;

import javax.validation.*;
import javax.validation.constraints.*;
import java.math.*;
import java.util.*;

@Value
@EqualsAndHashCode(callSuper = true)
public class OrderTo extends BaseTo {

    @NotNull
    List<PurchasedItemTo> items;

    @Valid
    @NotNull
    Status status;

    @NotNull
    @Range(min = 1, max = 10000000)
    @Digits(integer = 8, fraction = 2)
    BigDecimal total;

    String deliveryDate;


    public OrderTo(Integer id, List<PurchasedItemTo> items, Status status, BigDecimal total, String deliveryDate) {
        super(id);
        this.items = items;
        this.status = status;
        this.total = total;
        this.deliveryDate = deliveryDate;
    }

    public List<PurchasedItemTo> getItems() {
        return List.copyOf(items);
    }
}