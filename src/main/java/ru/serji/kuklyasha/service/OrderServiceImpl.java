package ru.serji.kuklyasha.service;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import ru.serji.kuklyasha.error.*;
import ru.serji.kuklyasha.model.*;
import ru.serji.kuklyasha.repository.*;

import java.math.*;
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
    public Order update(Order order, User user) {
        int id = order.id();
        checkNotFoundWithId(get(id, user).orElse(null), id);
        return repository.save(order);
    }

    @Override
    @Transactional
    public Order create(Set<PurchasedItem> items, User user) {
        items.forEach(item -> {
            if (item.getQuantity() > item.getDoll().getQuantity()) {
                throw new IllegalRequestDataException("not enough doll quantity with id = " + item.getDoll().id() + " in stock");
            }
            item.getDoll().setQuantity(item.getDoll().getQuantity() - item.getQuantity());
            dollService.save(item.getDoll());
        });
        BigDecimal total = items.stream().map(PurchasedItem::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        Order order = new Order(null, user, items, new Status(StatusType.NEW), total);
        return repository.save(order);
    }

    @Override
    public void delete(int id, User user) {
        checkModification(repository.deleteByIdAndUser(id, user), id);
    }
}