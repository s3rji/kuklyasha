package ru.serji.kuklyasha.service;

import ru.serji.kuklyasha.MatcherFactory;
import ru.serji.kuklyasha.model.Doll;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class DollTestData {
    public static final MatcherFactory.Matcher<Doll> DOLL_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Doll.class, "dateTime");

    public static final int DOLL_ID = 1;

    public static final int NOT_FOUND = 1000;

    public static final Doll doll = new Doll(DOLL_ID, "Doll1", "Pretty Doll", new BigDecimal("100.00"), "/image1", LocalDateTime.now());

    public static final List<Doll> allDolls = List.of(
            new Doll(DOLL_ID, "Doll1", "Pretty Doll", new BigDecimal("100.00"), "/image1", LocalDateTime.now()),
            new Doll(DOLL_ID + 1, "Doll2", "Pretty Doll", new BigDecimal("100.00"), "/image2", LocalDateTime.now()),
            new Doll(DOLL_ID + 2, "Doll3", "Pretty Doll", new BigDecimal("100.00"), "/image3", LocalDateTime.now()),
            new Doll(DOLL_ID + 3, "Doll4", "Pretty Doll", new BigDecimal("100.00"), "/image4", LocalDateTime.now()),
            new Doll(DOLL_ID + 4, "Doll5", "Pretty Doll", new BigDecimal("100.00"), "/image5", LocalDateTime.now())
    );

    public static Doll getNew() {
        return new Doll(null, "NewDoll", "Very Ugly Doll", new BigDecimal("100.00"), "/image1", LocalDateTime.now());
    }

    public static Doll getUpdated() {
        return new Doll(DOLL_ID, "UpdatedDoll", "Very Ugly Doll", new BigDecimal("100.00"), "/image1", LocalDateTime.now());
    }
}
