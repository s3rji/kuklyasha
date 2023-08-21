package ru.serji.kuklyasha.dto;

import lombok.*;

import java.util.*;

@Value
public class UserPage {
    List<UserTo> content;
    int total;
}