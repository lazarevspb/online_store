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


    @Transactional
    public void deleteProductToCartById(Long cartId, Long productId) {
        Cart cart = getCartById(cartId);
        CartItem cartItem = cart.getCartItemFromProductId(productId);

        if (cartItem != null) {
            cartItem.decrementQuantity();
            if (cartItem.getQuantity() <= 0) {
                cart.deleteItem(cartItem);
            }
            cart.recalculateTotalPrice();
            save(cart);
            return;
        } else {
            throw new ResourceNotFoundException("Не удалось найти элемент для удаления");
        }


    }

    @Transactional
    public void addProductToCartById(Long cartId, Long productId) {
        Cart cart = getCartById(cartId);
        CartItem cartItem = cart.getCartItemFromProductId(productId);
        save(cart);
        if (cartItem != null) {
//            log.warn(String.format("\nШаг №2: cartItem != null cart = %d, cartItem = %d", cart.getId(), cartItem.getProduct().getId()));

            cartItem.incrementQuantity();
            cart.recalculateTotalPrice();
            save(cart);
            return;
        }

        Product product = productService.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException(
                        String.format("Корзина с id: %d не найдена", cartId)));
        cartItem = new CartItem(product);
        cartItem.addProduct(product);

        cart.addItem(cartItem);
        cartRepository.save(cart);

        if (product != null) {
            log.warn(String.format("\nШаг №2: cart = %d, cartItem = %d,  product.getTitle() = %s",
                    cart.getId(), cartItem.getProduct().getId(), product.getTitle()));
        } else log.error("\nШаг №2: product = null");


    }

    private Cart getCartById(Long cartId) {
        return cartRepository.findById(cartId).orElse(new Cart());
    }


    private Cart getCart(Long cartId) {
        Cart cart;
        try {
            cart = cartRepository.findById(cartId).orElseThrow(
                    () -> new ResourceNotFoundException(
                            String.format("Корзина с id: %d не найдена", cartId)));
        } catch (ResourceNotFoundException e) {
            cart = new Cart();
            save(cart);
        }
        return cart;
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
