package ru.serji.kuklyasha.dto.order;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import ru.serji.kuklyasha.model.*;

import javax.validation.constraints.*;

@Value
public class StatusTo {

    @NotNull
    StatusType type;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public StatusTo(StatusType type) {
        this.type = type;
    }
}