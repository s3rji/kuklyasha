package ru.serji.kuklyasha;

import ru.serji.kuklyasha.dto.*;
import ru.serji.kuklyasha.model.*;
import ru.serji.kuklyasha.web.util.*;

import java.math.*;
import java.util.*;

public class DollTestData {
    public static final MatcherFactory.Matcher<Doll> DOLL_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Doll.class, "created");

    public static final MatcherFactory.Matcher<DollTo> DOLL_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(DollTo.class);

    public static final int DOLL_ID = 1;

    public static final int NOT_FOUND = 1000;

    public static final Doll doll = new Doll(DOLL_ID, "Doll1", "Pretty Doll", new BigDecimal("100.00"), 2, "/image1");

    public static final List<Doll> allDolls = List.of(
            new Doll(DOLL_ID, "Doll1", "Pretty Doll", new BigDecimal("100.00"), 2, "/image1"),
            new Doll(DOLL_ID + 1, "Doll2", "Pretty Doll", new BigDecimal("100.00"), 1, "/image2"),
            new Doll(DOLL_ID + 2, "Doll3", "Pretty Doll", new BigDecimal("100.00"), 1, "/image3"),
            new Doll(DOLL_ID + 3, "Doll4", "Pretty Doll", new BigDecimal("100.00"), 1, "/image4"),
            new Doll(DOLL_ID + 4, "Doll5", "Pretty Doll", new BigDecimal("100.00"), 1, "/image5")
    );

    public static Doll getNew() {
        return new Doll(null, "NewDoll", "Very Ugly Doll", new BigDecimal("100.00"), 1, "/image1");
    }

    public static Doll getUpdated() {
        return new Doll(DOLL_ID, "UpdatedDoll", "Very Ugly Doll", new BigDecimal("100.00"), 3, "/image1");
    }

    public static String jsonFromObject(BaseTo doll) {
        return JsonUtil.writeValue(doll);
    }
}