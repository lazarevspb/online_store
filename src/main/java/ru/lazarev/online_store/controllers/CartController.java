package ru.lazarev.online_store.controllers;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.lazarev.online_store.dto.CartDto;
import ru.lazarev.online_store.exception.ResourceNotFoundException;
import ru.lazarev.online_store.model.cart.Cart;
import ru.lazarev.online_store.model.cart.CartItem;
import ru.lazarev.online_store.model.product.Product;
import ru.lazarev.online_store.model.users.User;
import ru.lazarev.online_store.services.CartService;
import ru.lazarev.online_store.services.ProductService;
import ru.lazarev.online_store.services.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
@AllArgsConstructor
@Slf4j
public class CartController {
    private final CartService cartService;
    private final UserService userService;
    private final ProductService productService;

    @GetMapping
    public CartDto getCurrentCart(Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Cart cart = cartService.findCartByOwnerId(user.getId());
        log.info("{}", cart);
        return new CartDto(cart);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProductToCart(Principal principal, @RequestParam(name = "product_id") Long productId) {
        User user = userService
                .findByUsername(principal
                        .getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        cartService.addToCart(user.getId(), productId);
        return ResponseEntity.ok("");
    }

    @PostMapping
    public Cart updateCart(@RequestBody Cart cart) {
        return cartService.updateCart(cart);
    }

    @DeleteMapping
    public CartDto clearCart(Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Cart cart = cartService.clearCart(user.getId());
        return new CartDto(cart);
    }
}
