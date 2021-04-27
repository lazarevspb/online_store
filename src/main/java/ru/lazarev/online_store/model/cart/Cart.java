package ru.lazarev.online_store.model.cart;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.lazarev.online_store.model.users.User;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Slf4j
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

    public void addItem(CartItem cartItem) {
        for (CartItem item : items) {
            if (item.getProduct().getId().equals(cartItem.getProduct().getId())) {
                recalculateTotalPrice();
                return;
            }
        }
        this.items.add(cartItem);
        cartItem.setCart(this);
        recalculateTotalPrice();
        items.stream().map(cartItem1 -> cartItem1.getProduct().getTitle()).forEach(System.out::println);
    }

    public void recalculateTotalPrice() {
        totalPrice = 0;
        totalPrice = items.stream()
                .map(CartItem::getPrice)
                .reduce(Integer::sum).orElse(0);

    }

    public void mergeCart(Cart newCart) {
        newCart.items.forEach(this::addItem);
    }

    public CartItem getCartItemFromProductId(Long productId) {
        log.warn(String.format("items.size(): %d, productId: %d", items.size(), productId ));
        for (CartItem item : items) {
            if (item.getProduct().getId().equals(productId)) {
                return item;
            }
        }
        return null;


    }




}
