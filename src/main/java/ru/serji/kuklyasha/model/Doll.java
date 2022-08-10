package ru.serji.kuklyasha.model;

import lombok.*;
import org.hibernate.validator.constraints.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.*;
import java.math.*;
import java.time.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Doll")
public class Doll extends BaseEntity implements HasIdAndName {

    @NotBlank
    @Size(min = 2, max = 128)
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description", nullable = false)
    @NotBlank()
    @Size(min = 10)
    private String description;

    @Column(name = "price", nullable = false, precision = 8, scale = 2)
    @NotNull
    @Range(min = 1, max = 100000)
    @Digits(integer = 6, fraction = 2)
    private BigDecimal price;

    @Column(name = "image")
    private String image;

    @Column(name = "created", nullable = false)
    @NotNull
    private LocalDateTime created;

    public Doll(Integer id, String name, String description, BigDecimal price, String image, LocalDateTime created) {
        super(id);
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.created = created;
    }
}