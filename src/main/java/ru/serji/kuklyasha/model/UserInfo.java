package ru.serji.kuklyasha.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.*;
import javax.validation.constraints.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class UserInfo {

    @Column(name = "lastname")
    @Size(min = 2, max = 128)
    private String lastname;

    @Column(name = "phone", unique = true)
    @Pattern(regexp = "^(\\+7)?\\d{3}?\\d{3}?\\d{2}?\\d{2}$")
    private String phone;

    @Embedded
    @Valid
    private Address address;

    public UserInfo(String lastname, String phone, Address address) {
        this.lastname = lastname;
        this.phone = phone;
        this.address = address;
    }
}