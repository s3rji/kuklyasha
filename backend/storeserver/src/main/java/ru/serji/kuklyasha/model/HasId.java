package ru.serji.kuklyasha.model;

import com.fasterxml.jackson.annotation.*;

import java.util.*;

public interface HasId {
    Integer getId();

    void setId(Integer id);

    @JsonIgnore
    default boolean isNew() {
        return getId() == null;
    }

    default int id() {
        Objects.requireNonNull(getId(), "Entity must has id");
        return getId();
    }
}
