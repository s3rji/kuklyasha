package ru.serji.kuklyasha.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Column(name = "country")
    @Size(min = 2, max = 64)
    private String country;

    @Column(name = "city")
    @Size(min = 2, max = 64)
    private String city;

    @Column(name = "region")
    @Size(min = 2, max = 128)
    private String region;

    @Column(name = "street")
    @Size(min = 2, max = 256)
    private String street;

    @Column(name = "zipcode")
    @Pattern(regexp = "^\\d{6}$")
    private String zipcode;
}