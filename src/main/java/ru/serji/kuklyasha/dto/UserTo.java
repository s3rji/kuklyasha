package ru.serji.kuklyasha.dto;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import ru.serji.kuklyasha.model.*;

import javax.validation.constraints.*;

@Value
@EqualsAndHashCode(callSuper = true)
public class UserTo extends NamedTo implements HasIdAndEmail {

    @Email
    @NotBlank
    @Size(max = 128)
    String email;

    @NotBlank
    @Size(min = 5, max = 32)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;

    @Size(min = 2, max = 128)
    String lastname;

    @Pattern(regexp = "^(7)?\\d{3}?\\d{3}?\\d{2}?\\d{2}$")
    String phone;

    @Size(min = 2, max = 64)
    String country;

    @Size(min = 2, max = 64)
    String city;

    @Size(min = 2, max = 128)
    String region;

    @Size(min = 2, max = 256)
    String street;

    @Pattern(regexp = "^\\d{6}$")
    String zipcode;

    boolean emailNotice;

    boolean phoneNotice;

    public UserTo(Integer id, String name, String email, String password, String lastname, String phone, String country,
                  String city, String region, String street, String zipcode, boolean emailNotice, boolean phoneNotice) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.lastname = lastname;
        this.phone = phone;
        this.country = country;
        this.city = city;
        this.region = region;
        this.street = street;
        this.zipcode = zipcode;
        this.emailNotice = emailNotice;
        this.phoneNotice = phoneNotice;
    }

    @Override
    public String toString() {
        return "UserTo:" + id + '[' + email + ']';
    }
}