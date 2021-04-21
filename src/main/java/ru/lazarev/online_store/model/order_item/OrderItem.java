package ru.lazarev.online_store.model.order_item;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor
//@IdClass(OrderItemPK.class)
@Table(name = "order_items")
public class OrderItem implements Serializable {
    /*https://qastack.ru/programming/3585034/how-to-map-a-composite-key-with-jpa-and-hibernate*/
    @Id
    @Column(name = "order_id")
    private Long orderId;
    @Id
    @Column(name = "product_id")
    private Long productId;

    private Long count;
    private int pricePerItem;
    private int price;


}

