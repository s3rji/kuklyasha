package ru.serji.kuklyasha.dto;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

@Value
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties
public class UserChangeTo extends BaseTo {

    boolean enabled;

    public UserChangeTo(Integer id, boolean enabled) {
        super(id);
        this.enabled = enabled;
    }
}
