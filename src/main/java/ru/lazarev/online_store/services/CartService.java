package ru.lazarev.online_store.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lazarev.online_store.exception.ResourceNotFoundException;
import ru.lazarev.online_store.model.cart.Cart;
import ru.lazarev.online_store.model.cart.CartItem;
import ru.lazarev.online_store.model.product.Product;
import ru.lazarev.online_store.model.users.User;
import ru.lazarev.online_store.repositories.CartRepository;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    final  private  CartRepository cartRepository;
    final  private ProductService productService;

    public Cart updateCart(Cart cart) {
        recalculateCart(cart);
        return cartRepository.save(cart);
    }

    public Cart findCartByOwnerId(Long id) {
        Cart cart = cartRepository.findById(id).orElse(new Cart(id));
        return cartRepository.save(cart);
    }

    public Cart clearCart(Long id) {
        Cart cart = findCartByOwnerId(id);
        cart.getCartItems().clear();
        return cartRepository.save(cart);
    }

    private void recalculateCart(Cart cart) {
        cart.setPrice(new BigDecimal("0.0"));
        for (CartItem cartItem : cart.getCartItems()) {
            cart.setPrice(cart.getPrice().add(cartItem.getPrice()));
        }
    }

    public void addToCart(Long userId, Long productId) {
        Cart cart = findCartByOwnerId(userId);
        for (CartItem cartItem : cart.getCartItems()) {
            if (cartItem.getProductId().equals(productId)) {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                recalculateCart(cart);
                cartRepository.save(cart);
                return;
            }
        }

        System.out.println("productService\n                .findProductById(productId) = " + productService
                .findProductById(productId));

        CartItem item = new CartItem(productService
                .findProductById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found!")));
        System.out.println();
        System.out.println("item = " + item);
        System.out.println("cart = " + cart);
        System.out.println();
        cart.getCartItems().add(item);
        recalculateCart(cart);
        cartRepository.save(cart);
    }

}
