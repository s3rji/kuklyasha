package ru.serji.kuklyasha.model;

import java.util.*;

public interface HasId {
    Integer getId();

    void setId(Integer id);

    default boolean isNew() {
        return getId() == null;
    }

    default int id() {
        Objects.requireNonNull(getId(), "Entity must has id");
        return getId();
    }
}
