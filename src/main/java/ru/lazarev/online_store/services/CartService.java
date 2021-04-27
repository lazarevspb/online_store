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
    }

    public void addProductToCartById(Long cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(
                () -> new ResourceNotFoundException(
                        String.format("Корзина с id: %d не найдена", cartId)));
        CartItem cartItem = cart.getCartItemFromProductId(productId);

        if (cartItem != null) {

            cartItem.incrementQuantity();
            cart.recalculateTotalPrice();
            return;
        }

        Product product = productService.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException(
                        String.format("Корзина с id: %d не найдена", cartId)));
        cart.addItem(new CartItem(product));

    }

    @Transactional
    public Long getIdCartFromUsername(String username) {
        User user = getUser(username);
        return user.getCart().getId();
    }


    @Transactional
    public Long getCartForUser(String username, Long cartId) {
        if (username != null && cartId != null) {
            User user = getUser(username);
            Cart cart = cartRepository.findCartById(cartId).get();
            Optional<Cart> oldCart = getCartByUserID(user);

            if (oldCart.isPresent()) {
                cart.mergeCart(oldCart.get());
                cartRepository.delete(oldCart.get());
            }
            cart.setUser(user);
        }
        if (username == null) {
            Cart cart = cartRepository.save(new Cart());
            return cart.getId();
        }

        User user = getUser(username);
        Optional<Cart> cart = getCartByUserID(user);

        if (cart.isPresent()) {
            return cart.get().getId();
        }

        Cart newCart = new  Cart();
        newCart.setUser(user);
        cartRepository.save(cart.get());
        return newCart.getId();


    }

    private Optional<Cart> getCartByUserID(User user) {
        return cartRepository.findCartByUserId(user.getId());
    }


    private User getUser(String username) {
        return userService.findByUsername(username).get();
    }


}
