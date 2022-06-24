package ru.serji.kuklyasha.service;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import ru.serji.kuklyasha.model.*;
import ru.serji.kuklyasha.repository.*;

import java.util.*;

@Service
public class DollService {
    private final DollRepository dollRepository;

    @Autowired
    public DollService(DollRepository dollRepository) {
        this.dollRepository = dollRepository;
    }

    public Optional<Doll> get(int id) {
        return dollRepository.findById(id);
    }

    public List<Doll> getAll() {
        return dollRepository.findAll();
    }

    public Doll save(Doll doll) {
        return dollRepository.save(doll);
    }

    public void delete(int id) {
        dollRepository.deleteExisted(id);
    }
}