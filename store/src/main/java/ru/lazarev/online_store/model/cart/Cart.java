package ru.lazarev.online_store.model.cart;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@RedisHash("carts")
public class Cart {
    @Id
    private Long ownerId;

    private List<CartItem> cartItems;
    private BigDecimal price;

    public Cart(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Cart() {
        this.cartItems = new ArrayList<>();
    }

    public void clear() {
        cartItems.clear();
        recalculateTotalPrice();
    }

    public void deleteItem(CartItem cartItem) {
        if (this.cartItems != null) {
            this.cartItems.remove(cartItem);
        }
        recalculateTotalPrice();
    }

    public void addItem(CartItem cartItem) {
        if (this.cartItems != null) {
            for (CartItem item : this.cartItems) {
                if (item.getProduct().getId().equals(cartItem.getProduct().getId())) {
                    recalculateTotalPrice();
                    return;
                }
            }
            this.cartItems.add(cartItem); //Если в коллекции не нашлось такого итема
        } else {
            this.cartItems = new ArrayList<>();
            this.cartItems.add(cartItem);
        }
        cartItem.setCart(this);
        recalculateTotalPrice();
    }

    public void recalculateTotalPrice() {
//        price = 0;
//        price = cartItems.stream()
//                .map(CartItem::getPrice)
//                .reduce(Integer::sum).orElse(0);
    }

    public CartItem getCartItemFromProductId(Long productId) {
        if (cartItems != null) {
            for (CartItem item : cartItems) {
                if (item.getProduct().getId().equals(productId)) {
                    return item;
                }
            }
        }
        return null;
    }
}
