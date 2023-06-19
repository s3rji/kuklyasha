package ru.serji.kuklyasha.service;

import ru.serji.kuklyasha.model.*;

import java.util.*;

public interface OrderService {

    Optional<Order> get(int id, User user);

    List<Order> getAllFetchUser();

    List<Order> getAllByUser(User user);

    Order update(Order order, User user);

    void delete(int id, User user);

    Order create(Set<PurchasedItem> items, User user);
}