package ru.serji.kuklyasha.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "user_info")
@Getter
@Setter
@NoArgsConstructor
public class UserInfo extends BaseEntity implements HasId {

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", unique = true)
    @NotNull
    @Setter(AccessLevel.NONE)
    private User user;

    @Column(name = "lastname")
    @Size(min = 2, max = 128)
    private String lastname;

    @Column(name = "phone", unique = true)
    @Pattern(regexp = "^(\\+7)?\\d{3}?\\d{3}?\\d{2}?\\d{2}$")
    private String phone;

    @Embedded
    @Valid
    private Address address;

    public UserInfo(User user) {
        this.user = user;
    }

    public UserInfo(Integer id, User user, String lastname, String phone, Address address) {
        super(id);
        this.user = user;
        this.lastname = lastname;
        this.phone = phone;
        this.address = address;
    }
}