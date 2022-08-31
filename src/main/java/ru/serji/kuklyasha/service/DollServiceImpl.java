package ru.serji.kuklyasha.service;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;
import ru.serji.kuklyasha.model.*;
import ru.serji.kuklyasha.repository.*;

import java.util.*;

@Service
public class DollServiceImpl implements DollService {
    private final DollRepository dollRepository;

    @Autowired
    public DollServiceImpl(DollRepository dollRepository) {
        this.dollRepository = dollRepository;
    }

    @Override
    public Optional<Doll> get(int id) {
        return dollRepository.findById(id);
    }

    @Override
    public List<Doll> getAll() {
        return dollRepository.findAll();
    }

    @Override
    public Doll save(Doll doll) {
        return dollRepository.save(doll);
    }

    @Override
    public void delete(int id) {
        dollRepository.deleteExisted(id);
    }

    @Override
    public Optional<Doll> getByName(String name) {
        return dollRepository.findByName(name);
    }

    @Override
    public List<Doll> getLimitByPage(int page, int limit) {
        return dollRepository.findAll(PageRequest.of(page, limit, Sort.by("name"))).getContent();
    }

    public int totalCount() {
        return (int) dollRepository.count();
    }
}