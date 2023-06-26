package ru.serji.kuklyasha.repository;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.*;
import ru.serji.kuklyasha.model.*;

import java.util.*;

@Transactional(readOnly = true)
public interface OrderRepository extends BaseRepository<Order> {

    Optional<Order> findByIdAndUser(int id, User user);

    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.user")
    List<Order> findAllFetchUser();

    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.user")
    List<Order> findAllFetchUser(Pageable pageable);

    List<Order> findAllByUser(User user);

    int deleteByIdAndUser(int id, User user);
}