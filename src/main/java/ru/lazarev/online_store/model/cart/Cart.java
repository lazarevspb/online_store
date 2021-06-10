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
        this();
        this.ownerId = ownerId;
    }

    public Cart() {
        this.cartItems = new ArrayList<>();
    }
}
