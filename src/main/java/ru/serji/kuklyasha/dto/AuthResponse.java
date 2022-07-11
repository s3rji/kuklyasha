package ru.serji.kuklyasha.dto;

import lombok.*;

@Value
public class AuthResponse {
    String email;
    String token;
}
