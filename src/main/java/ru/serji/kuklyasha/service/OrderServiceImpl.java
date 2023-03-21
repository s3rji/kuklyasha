package ru.serji.kuklyasha.service;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import ru.serji.kuklyasha.error.*;
import ru.serji.kuklyasha.model.*;
import ru.serji.kuklyasha.repository.*;

import java.util.*;

import static ru.serji.kuklyasha.service.util.ValidationUtil.*;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;

    private final DollService dollService;

    @Autowired
    public OrderServiceImpl(OrderRepository repository, DollService dollService) {
        this.repository = repository;
        this.dollService = dollService;
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
    @Transactional
    public Order save(Order order, User user) {
        if (order.getId() != null) {
            int id = order.id();
            checkNotFoundWithId(get(id, user).orElse(null), id);
        } else {
            order.getItems().forEach(item -> {
                dollService.get(item.getDoll().id()).ifPresent(doll -> {
                    if (item.getQuantity() > doll.getQuantity()) {
                        throw new IllegalRequestDataException("not enough doll quantity with id = " + doll.id() + " in stock");
                    }
                    doll.setQuantity(doll.getQuantity() - item.getQuantity());
                    dollService.save(doll);
                });
            });
        }

        return repository.save(order);
    }

    @Override
    public void delete(int id, User user) {
        checkModification(repository.deleteByIdAndUser(id, user), id);
    }
}