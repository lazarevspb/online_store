package ru.lazarev.online_store.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.lazarev.online_store.dto.CartDto;
import ru.lazarev.online_store.exception.ResourceNotFoundException;
import ru.lazarev.online_store.model.cart.Cart;
import ru.lazarev.online_store.services.CartService;

import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping("/{id}")
    public CartDto getCartById(@PathVariable Long id) {
        log.warn(String.format("id: %d;", id));

        Cart cart = cartService.findCartById(id).orElseThrow(() ->
                new ResourceNotFoundException(
                        String.format("Не удалось найти корзину с id: %d", id)
                ));
        System.out.println("cart.getId() = " + cart.getId());
        return new CartDto(cart);
    }

    @GetMapping("/add/{productId}")
    public void addProductToCart(@PathVariable Long productId, Principal principal) {
        if (principal != null) {
            final Long cartId = cartService.getIdCartFromUsername(principal.getName());
            cartService.addProductToCartById(cartId, productId);

        } else {
            cartService.addProductToCartById(1L, productId);// временное решение
        }
    }

    @GetMapping("/clear/{cartId}")
    public void clearCart(@PathVariable Long cartId) {
        cartService.clearCartById(cartId);
    }
}
