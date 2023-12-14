package ru.serji.kuklyasha.dto;

import lombok.*;

import javax.validation.constraints.*;

@Value
public class AuthRequest {

    @Email
    @NotBlank
    @Size(max = 128)
    String email;

    @NotBlank
    @Size(min = 5, max = 32)
    String password;

    boolean rememberMe;
}
