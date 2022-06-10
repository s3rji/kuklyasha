package ru.serji.kuklyasha.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Doll")
public class Doll extends BaseEntity {

    @Column(name = "name", nullable = false, unique = true)
    @NotBlank
    @Size(min = 3, max = 30)
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

    @Column(name = "date_time", nullable = false)
    @NotNull
    private LocalDateTime dateTime;

    public Doll(Integer id, String name, String description, BigDecimal price, String image, LocalDateTime dateTime) {
        super(id);
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.dateTime = dateTime;
    }
}