package ru.serji.kuklyasha.dto.cart;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.validation.constraints.*;

@Value
public class CreatedCartItem {

    @NotNull
    Integer dollId;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public CreatedCartItem(Integer dollId) {
        this.dollId = dollId;
    }
}
