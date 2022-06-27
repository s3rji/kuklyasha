package ru.serji.kuklyasha.dto;

import lombok.*;

import javax.validation.constraints.*;

@Data
@EqualsAndHashCode(callSuper = true)
public class NamedTo extends BaseTo {

    @NotBlank
    @Size(min = 2, max = 128)
    protected String name;

    public NamedTo(Integer id, String name) {
        super(id);
        this.name = name;
    }

    @Override
    public String toString() {
        return super.toString() + '[' + name + ']';
    }
}
