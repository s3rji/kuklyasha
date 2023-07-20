package ru.serji.kuklyasha.repository;

import org.springframework.data.domain.*;
import ru.serji.kuklyasha.model.*;

import java.util.*;

public interface CustomOrderRepository {

    Optional<Order> findByIdAndUser(int id, User user);

    List<Order> findAllFetchUser();

    List<Order> findAllFetchUser(Pageable pageable);

    FilteredOrderPage findAllByFilterFetchUser(int page, int limit, String sort, String direction, String field, String filter);

    List<Order> findAllByUser(User user);

    int deleteByIdAndUser(int id, User user);

    Order save(Order order);

    int count();
}