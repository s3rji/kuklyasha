package ru.serji.kuklyasha.model;

import lombok.*;
import org.hibernate.validator.constraints.*;
import org.springframework.util.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.*;
import java.math.*;
import java.time.*;
import java.util.*;

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

    @Column(name = "poster")
    private String poster;

    @ElementCollection
    @CollectionTable(name = "gallery", joinColumns = @JoinColumn(name = "doll_id"))
    @Column(name = "filename")
    private Set<String> gallery;

    @Column(name = "created", nullable = false, columnDefinition = "timestamp default now()", updatable = false)
    @NotNull
    private LocalDateTime created;

    public Doll(Integer id, String name, String description, BigDecimal price, Integer quantity, String poster, Collection<String> gallery) {
        super(id);
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.poster = poster;
        this.created = LocalDateTime.now();
        setGallery(gallery);
    }

    public void setGallery(Collection<String> gallery) {
        this.gallery = CollectionUtils.isEmpty(gallery) ? Collections.emptySet() : Set.copyOf(gallery);
    }
}