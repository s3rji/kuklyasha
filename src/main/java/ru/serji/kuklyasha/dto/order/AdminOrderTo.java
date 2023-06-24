package ru.serji.kuklyasha.dto.order;

import lombok.*;
import org.hibernate.validator.constraints.*;
import ru.serji.kuklyasha.dto.*;
import ru.serji.kuklyasha.model.*;

import javax.validation.*;
import javax.validation.constraints.*;
import java.math.*;
import java.util.*;

@Value
@EqualsAndHashCode(callSuper = true)
public class AdminOrderTo extends BaseTo {

    @NotNull
    UserTo user;

    @NotNull
    List<PurchasedItemTo> items;

    @Valid
    @NotNull
    Status status;

    @NotNull
    @Range(min = 1, max = 10000000)
    @Digits(integer = 8, fraction = 2)
    BigDecimal total;

    @NotNull
    String deliveryDate;

    @NotNull
    String created;


    public AdminOrderTo(Integer id, UserTo user, List<PurchasedItemTo> items, Status status, BigDecimal total, String deliveryDate, String created) {
        super(id);
        this.user = user;
        this.items = items;
        this.status = status;
        this.total = total;
        this.deliveryDate = deliveryDate;
        this.created = created;
    }

    public List<PurchasedItemTo> getItems() {
        return List.copyOf(items);
    }
}