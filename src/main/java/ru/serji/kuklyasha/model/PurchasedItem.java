package ru.serji.kuklyasha.model;

import lombok.*;
import org.hibernate.annotations.*;
import org.hibernate.validator.constraints.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.*;

@Entity
@Table(name = "purchased_items")
@Immutable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PurchasedItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false, updatable = false)
    @NotNull
    private Order order;

    @ManyToOne(optional = false)
    @JoinColumn(name = "doll_id", nullable = false, updatable = false)
    @NotNull
    private Doll doll;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    @NotNull
    private User user;

    @Column(name = "quantity", nullable = false, updatable = false)
    @NotNull
    @Range(min = 1)
    private int quantity;

    @Column(name = "price", nullable = false, precision = 8, scale = 2, updatable = false)
    @NotNull
    @Range(min = 1, max = 100000)
    @Digits(integer = 6, fraction = 2)
    private BigDecimal price;

    public PurchasedItem(Integer id, Order order, Doll doll, User user, int quantity, BigDecimal price) {
        super(id);
        this.order = order;
        this.doll = doll;
        this.user = user;
        this.quantity = quantity;
        this.price = price;
    }
}