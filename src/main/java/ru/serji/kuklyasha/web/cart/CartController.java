package ru.serji.kuklyasha.web.cart;

import lombok.extern.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.transaction.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.*;
import ru.serji.kuklyasha.dto.*;
import ru.serji.kuklyasha.model.*;
import ru.serji.kuklyasha.service.*;
import ru.serji.kuklyasha.web.util.*;

import javax.validation.*;
import java.net.*;
import java.util.*;

import static ru.serji.kuklyasha.service.util.ValidationUtil.*;
import static ru.serji.kuklyasha.web.util.CartUtil.*;

@RestController
@RequestMapping(value = CartController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Log4j2
@Transactional(readOnly = true)
@CrossOrigin(origins = "http://localhost:3000")
public class CartController {
    final static String REST_URL = "/api/cart";

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public List<CartItemTo> getAllByUser() {
        User user = SecurityUtil.authUser();
        log.info("get all by user {}", user.id());
        return cartService.getAll(user).stream()
                .map(CartUtil::createToFromCartItem).sorted(Comparator.comparingInt(BaseTo::getId)).toList();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<CartItemTo> create(@Valid @RequestBody Doll doll) {
        log.info("create cart item from doll = {}", doll);
        Objects.requireNonNull(doll, "doll for cart item must not be null");
        User user = SecurityUtil.authUser();
        CartItem cartItem = new CartItem(null, doll, user, 1);
        CartItem created = cartService.save(cartItem, user);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL)
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(createToFromCartItem(created));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@Valid @RequestBody CartItemTo cartItemTo, @PathVariable("id") int id) {
        log.info("update cart item = {}", cartItemTo);
        Objects.requireNonNull(cartItemTo, "cart item must not be null");
        Objects.requireNonNull(cartItemTo.getDoll(), "cartItem.doll must not be null");
        assureIdConsistent(cartItemTo, id);
        User user = SecurityUtil.authUser();
        cartService.save(createCartItemFromTo(cartItemTo, user), user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void delete(@PathVariable("id") int id) {
        log.info("delete cart item with id = {}", id);
        User user = SecurityUtil.authUser();
        cartService.delete(id, user);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void deleteAllByUser() {
        User user = SecurityUtil.authUser();
        log.info("delete all cart items for user id = {}", user.id());
        cartService.deleteAll(user);
    }
}