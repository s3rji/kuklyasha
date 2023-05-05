package ru.serji.kuklyasha.dto;

import lombok.*;

import java.util.*;

@Value
public class DollPage {
    List<DollTo> content;
    int total;
}