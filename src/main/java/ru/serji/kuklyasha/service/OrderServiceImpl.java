package ru.serji.kuklyasha.service;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import ru.serji.kuklyasha.model.*;
import ru.serji.kuklyasha.repository.*;

import java.util.*;

import static ru.serji.kuklyasha.service.util.ValidationUtil.*;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;

    @Autowired
    public OrderServiceImpl(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Order> get(int id, User user) {
        return repository.findByIdAndUser(id, user);
    }

    @Override
    public List<Order> getAll(User user) {
        return repository.findAllByUser(user);
    }

    @Override
    public Order save(Order order, User user) {
        if (order.getId() != null) {
            int id = order.id();
            checkNotFoundWithId(get(id, user).orElse(null), id);
        }
        return repository.save(order);
    }

    @Override
    public void delete(int id, User user) {
        checkModification(repository.deleteByIdAndUser(id, user), id);
    }
}