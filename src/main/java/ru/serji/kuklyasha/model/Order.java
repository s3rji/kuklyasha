package ru.serji.kuklyasha.model;

import lombok.*;
import org.hibernate.validator.constraints.*;
import org.springframework.util.*;

import javax.persistence.*;
import javax.validation.*;
import javax.validation.constraints.*;
import java.math.*;
import java.time.*;
import java.util.*;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    @NotNull
    private User user;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "order_item",
            joinColumns = @JoinColumn(name = "order_id", nullable = false, updatable = false),
            inverseJoinColumns = @JoinColumn(name = "item_id", nullable = false, updatable = false)
    )
    @NotNull
    private Set<PurchasedItem> items = new HashSet<>();

    @Embedded
    @Valid
    @Setter
    @NotNull
    private Status status;

    @Column(name = "total", nullable = false, updatable = false, precision = 10, scale = 2)
    @NotNull
    @Range(min = 1, max = 10000000)
    @Digits(integer = 8, fraction = 2)
    private BigDecimal total;

    @Column(name = "created", nullable = false, updatable = false)
    @NotNull
    private LocalDateTime created;

    public Order(Integer id, User user, Set<PurchasedItem> items, Status status, BigDecimal total) {
        super(id);
        this.user = user;
        setItems(items);
        this.status = status;
        this.total = total;
        this.created = LocalDateTime.now();
    }

    public void setItems(Collection<PurchasedItem> items) {
        this.items = CollectionUtils.isEmpty(items) ? Collections.emptySet() : Set.copyOf(items);
    }

    public Set<PurchasedItem> getItems() {
        return Set.copyOf(items);
    }
}