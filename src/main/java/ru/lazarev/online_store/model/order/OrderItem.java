package ru.lazarev.online_store.model.order;

import lombok.NoArgsConstructor;
import ru.lazarev.online_store.model.product.Product;

import javax.persistence.*;
import java.io.Serializable;

@Entity
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

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "count")
    private int count;

    @Column(name = "price_per_item")
    private int pricePerItem;

    @Column(name = "price")
    private int price;
















//    @Id
//    @Column(name = "order_id")
//    private Long orderId;
//    @Id
//    @Column(name = "product_id")
//    private Long productId;
//
//    private Long count;
//    private int pricePerItem;
//    private int price;


}

