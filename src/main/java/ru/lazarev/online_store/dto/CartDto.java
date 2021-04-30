package ru.lazarev.online_store.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.lazarev.online_store.model.cart.Cart;
import ru.lazarev.online_store.model.cart.CartItem;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CartDto {
    private List<CartItemDto> items;
    private int totalPrice;

    public CartDto(Cart cart) {
        List<CartItemDto> list = new ArrayList<>();
        List<CartItem> cartItems = cart.getItems();
        for (int i = 0, cartItemsSize = cartItems.size(); i < cartItemsSize; i++) {
            CartItem cartItem = cartItems.get(i);
            CartItemDto cartItemDto = new CartItemDto(cartItem);
            list.add(cartItemDto);
        }
        this.items = list;
        this.totalPrice = cart.getTotalPrice();
    }
}
