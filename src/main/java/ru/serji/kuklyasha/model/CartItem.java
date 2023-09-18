package ru.serji.kuklyasha.model;

import lombok.*;
import org.hibernate.validator.constraints.*;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "cart_items", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "doll_id"}, name = "uk_user_doll"))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItem extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "doll_id", nullable = false, updatable = false)
    @NotNull
    private Doll doll;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    @NotNull
    private User user;

    @Column(name = "quantity", nullable = false, columnDefinition = "integer default 1")
    @Setter
    @NotNull
    @Range(min = 1)
    private int quantity;

    public CartItem(Integer id, Doll doll, User user, int quantity) {
        super(id);
        this.doll = doll;
        this.user = user;
        this.quantity = quantity;
    }
}