package ru.serji.kuklyasha.web.util;

import lombok.experimental.*;
import ru.serji.kuklyasha.dto.*;
import ru.serji.kuklyasha.model.*;

import java.util.*;

@UtilityClass
public class DollUtil {

    public static DollPage createDollPage(List<Doll> content, int total) {
        return new DollPage(content, total);
    }
}