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
    Set<Doll> items;

    @Valid
    @NotNull
    Status status;

    @NotNull
    @Range(min = 1, max = 10000000)
    @Digits(integer = 8, fraction = 2)
    BigDecimal total;


    public OrderTo(Integer id, Set<Doll> items, Status status, BigDecimal total) {
        super(id);
        this.items = items;
        this.status = status;
        this.total = total;
    }

    public Set<Doll> getItems() {
        return Set.copyOf(items);
    }
}