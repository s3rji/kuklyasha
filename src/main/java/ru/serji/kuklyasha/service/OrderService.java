package ru.serji.kuklyasha.service;

import ru.serji.kuklyasha.model.*;

import java.util.*;

public interface OrderService {

    Optional<Order> get(int id, User user);

    List<Order> getAllFetchUser();

    List<Order> getLimitFetchUserAndSort(int page, int limit, String sort, String direction);

    List<Order> getAllByUser(User user);

    Order update(Order order, User user);

    void delete(int id, User user);

    Order create(Order order);
}