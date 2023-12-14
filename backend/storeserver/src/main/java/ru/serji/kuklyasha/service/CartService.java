package ru.serji.kuklyasha.service;

import ru.serji.kuklyasha.model.*;

import java.util.*;

public interface CartService {

    Optional<CartItem> get(int id, User user);

    List<CartItem> getAll(User user);

    CartItem save(CartItem cartItem, User user);

    void delete(int id, User user);

    Optional<CartItem> getByDoll(Doll doll, User user);

    void deleteAll(User user);
}