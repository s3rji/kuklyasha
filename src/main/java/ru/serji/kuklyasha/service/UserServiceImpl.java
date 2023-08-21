package ru.serji.kuklyasha.service;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;
import ru.serji.kuklyasha.model.*;
import ru.serji.kuklyasha.repository.*;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<User> get(int id) {
        return repository.findById(id);
    }

    @Override
    public Optional<User> getByEmailIgnoreCase(String email) {
        return repository.findByEmailIgnoreCase(email);
    }

    @Override
    public Optional<User> getByPhone(String phone) {
        return repository.findByPhone(phone);
    }

    @Override
    public List<User> getAll() {
        return repository.findAll();
    }

    @Override
    public List<User> getLimitByPage(int page, int limit) {
        return repository.findAll(PageRequest.of(page, limit, Sort.by("id"))).getContent();
    }

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public void delete(int id) {
        repository.deleteExisted(id);
    }

    @Override
    public int totalCount() {
        return (int) repository.count();
    }
}
