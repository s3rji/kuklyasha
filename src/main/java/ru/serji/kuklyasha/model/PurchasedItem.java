package ru.serji.kuklyasha.model;

import lombok.*;
import org.hibernate.annotations.*;
import org.hibernate.validator.constraints.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "purchased_item")
@Immutable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PurchasedItem extends BaseEntity {

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

    public PurchasedItem(Integer id, Doll doll, User user, int quantity) {
        super(id);
        this.doll = doll;
        this.user = user;
        this.quantity = quantity;
    }
}