package ru.serji.kuklyasha.service;

import ru.serji.kuklyasha.model.*;

import java.util.*;

public interface DollService {
    Optional<Doll> get(int id);

    List<Doll> getAll();

    Doll save(Doll doll);

    void delete(int id);

    Optional<Doll> getByName(String name);
}