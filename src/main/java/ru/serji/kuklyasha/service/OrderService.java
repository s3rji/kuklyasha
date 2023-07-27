package ru.serji.kuklyasha.service;

import ru.serji.kuklyasha.model.*;

import java.util.*;

public interface OrderService {

    Optional<Order> getById(int id);

    Optional<Order> getByIdAndUser(int id, User user);

    List<Order> getAllFetchUser();

    List<Order> getLimitFetchUserAndSort(int page, int limit, String sort, String direction);

    FilteredOrderPage getLimitByFilterFetchUserAndSort(int page, int limit, String sort, String direction, String field, String filter);

    List<Order> getAllByUser(User user);

    Order update(Order order);

    void delete(int id, User user);

    Order create(Order order);

    int totalCount();
}