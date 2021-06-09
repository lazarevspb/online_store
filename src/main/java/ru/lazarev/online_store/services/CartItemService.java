package ru.lazarev.online_store.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lazarev.online_store.exception.ResourceNotFoundException;
import ru.lazarev.online_store.model.cart.Cart;
import ru.lazarev.online_store.model.cart.CartItem;
import ru.lazarev.online_store.repositories.CartItemRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CartItemService {
    private CartItemRepository cartItemRepository;
    private ProductService productService;

    public CartItem addToCart(Cart cart, Long productId) {
        CartItem cartItem = new CartItem(productService.findProductById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("No such product")));
        cartItem.setCart(cart);
        cartItemRepository.save(cartItem);
        return cartItemRepository.save(cartItem);
    }

    public Optional<CartItem> findById(Long id) {
        return cartItemRepository.findById(id);
    }

    public CartItem saveOrUpdate(CartItem item) {
        return cartItemRepository.save(item);
    }


}
