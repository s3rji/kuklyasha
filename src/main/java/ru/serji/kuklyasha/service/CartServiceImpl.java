package ru.serji.kuklyasha.service;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import ru.serji.kuklyasha.model.*;
import ru.serji.kuklyasha.repository.*;

import java.util.*;

import static ru.serji.kuklyasha.service.util.ValidationUtil.*;


@Service
public class CartServiceImpl implements CartService {

    private final CartRepository repository;

    @Autowired
    public CartServiceImpl(CartRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<CartItem> get(int id, User user) {
        return repository.findByIdAndUser(id, user);
    }

    @Override
    public List<CartItem> getAll(User user) {
        return repository.findAllByUser(user);
    }

    @Override
    public CartItem save(CartItem cartItem, User user) {
        if (cartItem.getId() != null) {
            int id = cartItem.id();
            checkNotFoundWithId(get(id, user).orElse(null), id);
        }
        return repository.save(cartItem);
    }

    @Override
    public void delete(int id, User user) {
        checkModification(repository.deleteByIdAndUser(id, user), id);
    }

    @Override
    public Optional<CartItem> getByDoll(Doll doll, User user) {
        return repository.findByDollAndUser(doll, user);
    }
}