package ru.serji.kuklyasha.model;

import lombok.*;
import org.hibernate.validator.constraints.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.*;
import java.math.*;
import java.time.*;

@Entity
@Table(name = "doll")
@Getter
@Setter
@NoArgsConstructor
public class Doll extends BaseEntity implements HasIdAndName {

    @Column(name = "name", nullable = false, unique = true)
    @NotBlank
    @Size(min = 2, max = 128)
    private String name;

    @Column(name = "description", nullable = false)
    @NotBlank
    @Size(min = 10)
    private String description;

    @Column(name = "price", nullable = false, precision = 8, scale = 2)
    @NotNull
    @Range(min = 1, max = 100000)
    @Digits(integer = 6, fraction = 2)
    private BigDecimal price;

    @Column(name = "quantity", nullable = false, columnDefinition = "integer default 0")
    @NotNull
    @Range(min = 0)
    private Integer quantity;

    @Column(name = "image")
    private String image;

    @Column(name = "created", nullable = false, columnDefinition = "timestamp default now()", updatable = false)
    @NotNull
    private LocalDateTime created;

    public Doll(Integer id, String name, String description, BigDecimal price, Integer quantity, String image) {
        super(id);
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.created = LocalDateTime.now();
    }
}