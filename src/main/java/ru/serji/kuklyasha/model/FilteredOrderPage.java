package ru.serji.kuklyasha.model;

import lombok.*;

import java.util.*;

@Value
public class FilteredOrderPage {
    List<Order> content;
    int total;
}