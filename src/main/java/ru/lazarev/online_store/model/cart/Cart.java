package ru.lazarev.online_store.model.cart;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.lazarev.online_store.model.users.User;

import javax.persistence.*;
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

    @OneToMany(mappedBy = "cart")
    private List<CartItem> items;

    @Column(name = "price")
    private int totalPrice;


    public void clear() {
        items.clear();
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
    }

    public void recalculateTotalPrice() {
        totalPrice = 0;
        totalPrice = items.stream()
                .map(CartItem::getPrice)
                .reduce(Integer::sum).get();

    }

    public void mergeCart(Cart newCart) {
        newCart.items.forEach(this::addItem);
    }


}
