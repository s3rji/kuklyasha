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

    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @NotNull
    private Set<PurchasedItem> items = new HashSet<>();

    @Embedded
    @Valid
    @Setter
    @NotNull
    private Status status;

    @Column(name = "total", nullable = false, updatable = false, precision = 10, scale = 2)
    @Setter
    @NotNull
    @Range(min = 1, max = 10000000)
    @Digits(integer = 8, fraction = 2)
    private BigDecimal total;

    @Column(name = "delivery_date", nullable = false)
    @NotNull
    private LocalDate deliveryDate;

    @Column(name = "created", nullable = false, updatable = false)
    @NotNull
    private LocalDateTime created;

    public Order(Integer id, User user, Status status) {
        super(id);
        this.user = user;
        this.status = status;
        this.total = new BigDecimal(0);
        this.created = LocalDateTime.now();
        this.deliveryDate = created.toLocalDate().plusDays(4);
    }

    public Order(Integer id, User user, Set<PurchasedItem> items, Status status, BigDecimal total) {
        super(id);
        this.user = user;
        setItems(items);
        this.status = status;
        this.total = total;
        this.created = LocalDateTime.now();
        this.deliveryDate = created.toLocalDate().plusDays(4);
    }

    public Order(Integer id, User user, Set<PurchasedItem> items, Status status, BigDecimal total, LocalDate deliveryDate) {
        super(id);
        this.user = user;
        setItems(items);
        this.status = status;
        this.total = total;
        this.created = LocalDateTime.now();
        this.deliveryDate = deliveryDate;
    }

    public void setItems(Collection<PurchasedItem> items) {
        this.items = CollectionUtils.isEmpty(items) ? Collections.emptySet() : Set.copyOf(items);
    }

    public Set<PurchasedItem> getItems() {
        return Set.copyOf(items);
    }

    public void addItem(PurchasedItem item) {
        items.add(item);
    }
}