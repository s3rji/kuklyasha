package ru.serji.kuklyasha.repository;

import org.springframework.transaction.annotation.*;
import ru.serji.kuklyasha.model.*;

import java.util.*;

@Transactional(readOnly = true)
public interface OrderRepository extends BaseRepository<Order> {

    List<Order> findAllByUser(User user);;
}