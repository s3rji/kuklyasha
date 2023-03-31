package ru.serji.kuklyasha.service;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import ru.serji.kuklyasha.error.*;
import ru.serji.kuklyasha.model.*;
import ru.serji.kuklyasha.repository.*;

import java.util.*;

import static ru.serji.kuklyasha.service.util.ValidationUtil.*;


@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final DollRepository dollRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, DollRepository dollRepository) {
        this.cartRepository = cartRepository;
        this.dollRepository = dollRepository;
    }

    @Override
    public Optional<CartItem> get(int id, User user) {
        return cartRepository.findByIdAndUser(id, user);
    }

    @Override
    public List<CartItem> getAll(User user) {
        return cartRepository.findAllByUser(user);
    }

    @Override
    public CartItem save(CartItem cartItem, User user) {
        if (cartItem.getId() != null) {
            int id = cartItem.id();
            checkNotFoundWithId(get(id, user).orElse(null), id);
        } else {
            checkValidState(cartItem, user);
        }
        return cartRepository.save(cartItem);
    }

    private void checkValidState(CartItem cartItem, User user) {
        getByDoll(cartItem.getDoll(), user).ifPresent(ci -> {
            throw new IllegalRequestDataException("cart item with same doll already exist");
        });

        Optional<Doll> doll = dollRepository.findById(cartItem.getDoll().id());
        if (doll.isEmpty()) {
            throw new IllegalRequestDataException("doll entity with id " + cartItem.getDoll().id() + " not found");
        } else {
            if (cartItem.getQuantity() > doll.get().getQuantity()) {
                throw new IllegalRequestDataException("not enough doll quantity in stock");
            }
        }
    }

    @Override
    public void delete(int id, User user) {
        checkModification(cartRepository.deleteByIdAndUser(id, user), id);
    }

    @Override
    public Optional<CartItem> getByDoll(Doll doll, User user) {
        return cartRepository.findByDollAndUser(doll, user);
    }

    @Override
    public void deleteAll(User user) {
        checkModification(cartRepository.deleteAllByUser(user));
    }
}