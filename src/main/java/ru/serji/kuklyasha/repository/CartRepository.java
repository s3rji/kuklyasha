package ru.serji.kuklyasha.repository;

import org.springframework.transaction.annotation.*;
import ru.serji.kuklyasha.model.*;

import java.util.*;

@Transactional(readOnly = true)
public interface CartRepository extends BaseRepository<CartItem> {

    Optional<CartItem> findByDollAndUser(Doll doll, User user);

    Optional<CartItem> findByIdAndUser(int id, User user);

    List<CartItem> findAllByUser(User user);

    int deleteByIdAndUser(int id, User user);
}