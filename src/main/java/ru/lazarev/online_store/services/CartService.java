package ru.lazarev.online_store.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lazarev.online_store.exception.ResourceNotFoundException;
import ru.lazarev.online_store.model.cart.Cart;
import ru.lazarev.online_store.model.cart.CartItem;
import ru.lazarev.online_store.model.product.Product;
import ru.lazarev.online_store.model.users.User;
import ru.lazarev.online_store.repositories.CartRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final UserService userService;
    private final ProductService productService;

    public Optional<Cart> findCartById(Long id) {
        return cartRepository.findCartById(id);
    }

    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    public void clearCartById(Long id) {
        final Cart cart = findCartById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Корзина с id: %d не найдена", id)));
        cart.clear();
        save(cart);
    }

    public void addProductToCartById(Long cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId).orElse(new Cart());
        save(cart);
        log.warn(String.format("cart id = %d", cart.getId()));
        CartItem cartItem = cart.getCartItemFromProductId(productId);

        if (cartItem != null) {

            cartItem.incrementQuantity();
            cart.recalculateTotalPrice();
            save(cart);
            return;
        }

        Product product = productService.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException(
                        String.format("Корзина с id: %d не найдена", cartId)));
        cart.addItem(new CartItem(product));
        save(cart);
    }

    @Transactional
    public Long getIdCartFromUsername(String username) {
        User user = getUser(username);
        return user.getCart().getId();
    }

    private User getUser(String username) {
        return userService.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException(
                String.format("Пользователь с username %s не найден", username)
        ));
    }
}
