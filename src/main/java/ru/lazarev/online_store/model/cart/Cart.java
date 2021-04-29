package ru.lazarev.online_store.model.cart;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.lazarev.online_store.model.users.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(mappedBy = "cart")
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items;

    @Column(name = "price")
    private int totalPrice;

    public void clear() {
        items.clear();
        recalculateTotalPrice();
    }

    public void deleteItem(CartItem cartItem) {
        if (this.items != null) {
            this.items.remove(cartItem);
        }
        recalculateTotalPrice();
    }

    public void addItem(CartItem cartItem) {
        if (this.items != null) {
            for (CartItem item : this.items) {
                if (item.getProduct().getId().equals(cartItem.getProduct().getId())) {
                    recalculateTotalPrice();
                    return;
                }
            }
            this.items.add(cartItem); //Если в коллекции не нашлось такого итема
        } else {
            this.items = new ArrayList<>();
            this.items.add(cartItem);
        }
        cartItem.setCart(this);
        recalculateTotalPrice();
    }

    public void recalculateTotalPrice() {
        totalPrice = 0;
        totalPrice = items.stream()
                .map(CartItem::getPrice)
                .reduce(Integer::sum).orElse(0);
    }

    public CartItem getCartItemFromProductId(Long productId) {
        if (items != null) {
            for (CartItem item : items) {
                if (item.getProduct().getId().equals(productId)) {
                    return item;
                }
            }
        }
        return null;
    }
}
