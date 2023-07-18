package ru.serji.kuklyasha.service;

import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import ru.serji.kuklyasha.model.*;
import ru.serji.kuklyasha.repository.*;

import java.util.*;

import static ru.serji.kuklyasha.service.util.ValidationUtil.*;

@Service
public class OrderServiceImpl implements OrderService {

    private final CustomOrderRepository repository;

    @Autowired
    public OrderServiceImpl(CustomOrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Order> get(int id, User user) {
        return repository.findByIdAndUser(id, user);
    }

    @Override
    public List<Order> getAllFetchUser() {
        return repository.findAllFetchUser();
    }

    @Override
    public List<Order> getLimitFetchUserAndSort(int page, int limit, String sort, String direction) {
        Sort sortOption = Sort.by(Sort.Direction.fromString(direction), sort).and(Sort.by("id"));
        return repository.findAllFetchUser(PageRequest.of(page, limit, sortOption));
    }

    @Override
    public List<Order> getLimitByFilterFetchUserAndSort(int page, int limit, String sort, String direction, String field, String filter) {
        return repository.findAllByFilterFetchUser(page, limit, sort, direction, field, filter);
    }

    @Override
    public List<Order> getAllByUser(User user) {
        return repository.findAllByUser(user);
    }

    @Override
    @Transactional
    public Order update(Order order, User user) {
        int id = order.id();
        checkNotFoundWithId(get(id, user).orElse(null), id);
        return repository.save(order);
    }

    @Override
    @Transactional
    public Order create(Order order) {
        return repository.save(order);
    }

    @Override
    public void delete(int id, User user) {
        checkModification(repository.deleteByIdAndUser(id, user), id);
    }
}