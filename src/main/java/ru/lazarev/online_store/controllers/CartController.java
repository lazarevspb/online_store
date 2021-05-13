package ru.lazarev.online_store.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final UserService userService;
    private final ProductService productService;

    @GetMapping
    public Cart getCurrentCart(Principal principal) {

        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return cartService.findCartByOwnerId(user.getId());
    }

    @PostMapping
    public Cart updateCart(@RequestBody Cart cart) {
        return cartService.updateCart(cart);
    }

    @DeleteMapping
    public Cart clearCart(Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return cartService.clearCart(user.getId());
    }

    @GetMapping("/mock")
    public Cart getMockCart(Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Cart cart = new Cart();
        List<CartItem> items = new ArrayList<>();
        List<Product> products = productService.getAllProducts();
        for (Product product : products) {
            items.add(new CartItem(product));
        }
        cart.setOwnerId(1L);
        cart.setCartItems(items);

        return cart;
    }
}
