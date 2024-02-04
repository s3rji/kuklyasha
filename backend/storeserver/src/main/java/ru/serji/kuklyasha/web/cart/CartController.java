package ru.serji.kuklyasha.web.cart;

import lombok.extern.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.transaction.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.*;
import ru.serji.kuklyasha.dto.*;
import ru.serji.kuklyasha.dto.cart.*;
import ru.serji.kuklyasha.error.*;
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
//@CrossOrigin(origins = ReactAppProperties.HOST_NAME)
public class CartController {
    final static String REST_URL = "/api/cart";

    private final CartService cartService;
    private final DollService dollService;

    @Autowired
    public CartController(CartService cartService, DollService dollService) {
        this.cartService = cartService;
        this.dollService = dollService;
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
    public ResponseEntity<CartItemTo> create(@Valid @RequestBody CreatedCartItem cartItemTo) {
        log.info("create cart item for doll with id = {}", cartItemTo.getDollId());
        Objects.requireNonNull(cartItemTo, "cart item must not be null");
        User user = SecurityUtil.authUser();
        Optional<Doll> doll = dollService.get(cartItemTo.getDollId());
        if (doll.isPresent()) {
            CartItem cartItem = new CartItem(null, doll.get(), user, 1);
            CartItem created = cartService.save(cartItem, user);
            URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(REST_URL)
                    .buildAndExpand(created.getId()).toUri();
            return ResponseEntity.created(uriOfNewResource).body(createToFromCartItem(created));
        } else {
            throw new IllegalRequestDataException("Action could not be processed properly due to invalid data provided");
        }
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@Valid @RequestBody UpdatedCartItem cartItemTo, @PathVariable("id") int id) {
        log.info("update cart item = {}", cartItemTo);
        Objects.requireNonNull(cartItemTo, "cart item must not be null");
        assureIdConsistent(cartItemTo, id);
        User user = SecurityUtil.authUser();
        cartService.get(cartItemTo.id(), user).ifPresent(item -> {
            item.setQuantity(cartItemTo.getQuantity());
            cartService.save(item, user);
        });
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