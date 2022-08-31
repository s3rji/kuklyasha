package ru.serji.kuklyasha.dto;

import lombok.*;
import ru.serji.kuklyasha.model.*;

import java.util.*;

@Value
public class DollPage {
    List<Doll> content;
    int total;
}