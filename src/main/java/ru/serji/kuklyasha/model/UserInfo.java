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

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", unique = true)
    @NotNull
    private User user;

    @Column(name = "lastname")
    @NotBlank
    @Size(min = 2, max = 128)
    private String lastname;

    @Column(name = "phone", unique = true)
    @NotBlank
    @Pattern(regexp = "^(\\+7)?\\d{3}?\\d{3}?\\d{2}?\\d{2}$")
    private String phone;

    @Embedded
    @Valid
    private Address address;

    public UserInfo(Integer id, User user, String lastname, String phone, Address address) {
        super(id);
        this.user = user;
        this.lastname = lastname;
        this.phone = phone;
        this.address = address;
    }
}