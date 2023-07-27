package ru.serji.kuklyasha.dto.order;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import ru.serji.kuklyasha.dto.*;
import ru.serji.kuklyasha.model.*;

import javax.validation.constraints.*;

@Value
@EqualsAndHashCode(callSuper = true)
public class OrderChangeTo extends BaseTo {

    @NotNull
    StatusType type;

    @NotNull
    String deliveryDate;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public OrderChangeTo(Integer id, StatusType type, String deliveryDate) {
        super(id);
        this.type = type;
        this.deliveryDate = deliveryDate;
    }
}