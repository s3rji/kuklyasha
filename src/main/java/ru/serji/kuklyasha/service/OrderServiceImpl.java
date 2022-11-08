package ru.serji.kuklyasha.service;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import ru.serji.kuklyasha.model.*;
import ru.serji.kuklyasha.repository.*;

import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;

    @Autowired
    public OrderServiceImpl(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Order> get(int id) {
        return repository.findById(id);
    }

    @Override
    public List<Order> getAllByUser(User user) {
        return repository.findAllByUser(user);
    }

    @Override
    public Order save(Order order) {
        return repository.save(order);
    }

    @Override
    public void delete(int id) {
        repository.deleteExisted(id);
    }
}