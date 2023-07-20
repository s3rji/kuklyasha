package ru.serji.kuklyasha.dto.order;

import lombok.*;

import java.util.*;

@Value
public class AdminOrderPage {
    List<AdminOrderTo> content;
    int total;
}