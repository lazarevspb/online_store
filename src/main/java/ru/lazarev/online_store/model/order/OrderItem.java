package ru.lazarev.online_store.model.order;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.lazarev.online_store.model.cart.CartItem;
import ru.lazarev.online_store.model.product.Product;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "order_items")
public class OrderItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private Long productId;

    @Column(name = "title")
    private String title;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price_per_product")
    private BigDecimal pricePerProduct;

    @Column(name = "price")
    private BigDecimal price;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public OrderItem(CartItem cartItem) {
        this.productId = cartItem.getProductId();
        this.title = cartItem.getTitle();
        this.quantity = cartItem.getQuantity();
        this.pricePerProduct = cartItem.getPricePerProduct();
        this.price = cartItem.getPrice();
    }
}