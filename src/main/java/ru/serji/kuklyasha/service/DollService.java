package ru.serji.kuklyasha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.serji.kuklyasha.model.Doll;
import ru.serji.kuklyasha.repository.DollRepository;

import java.util.List;
import java.util.Optional;

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