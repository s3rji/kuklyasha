package ru.serji.kuklyasha.service;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;
import ru.serji.kuklyasha.model.*;
import ru.serji.kuklyasha.repository.*;

import java.util.*;

@Service
public class DollServiceImpl implements DollService {
    private final DollRepository repository;

    @Autowired
    public DollServiceImpl(DollRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Doll> get(int id) {
        return repository.findById(id);
    }

    @Override
    public List<Doll> getAll() {
        return repository.findAll();
    }

    @Override
    public Doll save(Doll doll) {
        return repository.save(doll);
    }

    @Override
    public void delete(int id) {
        repository.deleteExisted(id);
    }

    @Override
    public Optional<Doll> getByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<Doll> getLimitByPage(int page, int limit) {
        return repository.findAll(PageRequest.of(page, limit, Sort.by("name"))).getContent();
    }

    public int totalCount() {
        return (int) repository.count();
    }
}