package ru.serji.kuklyasha.dto;

import lombok.*;
import org.hibernate.validator.constraints.*;
import org.springframework.util.*;
import ru.serji.kuklyasha.model.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.*;
import java.math.*;
import java.util.*;

@Value
@EqualsAndHashCode(callSuper = true)
public class DollTo extends BaseTo implements HasIdAndName {

    @NotBlank
    @Size(min = 2, max = 128)
    String name;

    @NotBlank
    @Size(min = 10)
    String description;

    @NotNull
    @Range(min = 1, max = 100000)
    @Digits(integer = 6, fraction = 2)
    BigDecimal price;

    @NotNull
    @Range(min = 0)
    Integer quantity;

    @NotNull
    String poster;

    List<String> gallery;


    public DollTo(Integer id, String name, String description, BigDecimal price, Integer quantity, String poster, Collection<String> gallery) {
        super(id);
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.poster = poster;
        this.gallery = CollectionUtils.isEmpty(gallery) ? Collections.emptyList() : List.copyOf(gallery);
    }
}