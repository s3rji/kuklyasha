package ru.serji.kuklyasha.web.util;

import lombok.experimental.*;
import org.springframework.lang.*;
import ru.serji.kuklyasha.dto.*;
import ru.serji.kuklyasha.model.*;

import java.util.*;

@UtilityClass
public class DollUtil {

    public static DollTo createToFromDoll(@NonNull Doll doll) {
        return new DollTo(doll.getId(), doll.getName(), doll.getDescription(), doll.getPrice(),
                doll.getQuantity(), doll.getPoster(), doll.getGallery());
    }

    public static Doll createDollFromTo(@NonNull DollTo dollTo) {
        return new Doll(dollTo.getId(), dollTo.getName(), dollTo.getDescription(), dollTo.getPrice(),
                dollTo.getQuantity(), dollTo.getPoster(), dollTo.getGallery());
    }

    public static DollPage createDollPage(List<DollTo> content, int total) {
        return new DollPage(content, total);
    }
}