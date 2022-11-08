package ru.serji.kuklyasha.service;

import ru.serji.kuklyasha.model.*;

import java.util.*;

public interface OrderService {

    Optional<Order> get(int id);

    List<Order> getAllByUser(User user);

    Order save(Order order);

    void delete(int id);
}