package ru.lazarev.online_store.model.order;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.lazarev.online_store.model.delivery.DeliveryDetails;
import ru.lazarev.online_store.model.product.PromotionalEvents;
import ru.lazarev.online_store.model.users.User;
import ru.lazarev.online_store.model.cart.Cart;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "orders")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;


  @ManyToOne
  @JoinColumn(name = "user_id")
  private User owner;

  @OneToMany(mappedBy = "order")
  private List<OrderItem> orderItems;

//  @OneToOne
//  @JoinColumn(name = "cart_id")
//  private Cart cart;


  @OneToOne
  @JoinColumn(name = "promotional_event_id")
  private PromotionalEvents promotionalEvent;

  @Column(name = "discount")
  private int discount;

  @Column(name = "total_discount")
  private int totalDiscount;

  @Column(name = "t_price_w_discount")
  private int tPriceWDiscount;

  @Column(name = "total_price")
  private Integer totalPrice;

  @Column(name = "phone")
  private String phone;

  @Column(name = "address")
  private String address;

  @Column(name = "delivery_required")
  private boolean deliveryRequired;

  @OneToOne
  @JoinColumn(name = "delivery_details_id")
  private DeliveryDetails deliveryDetails;

  @OneToOne
  @JoinColumn(name = "order_status_id")
  private OrderStatus orderStatus;

  @Column(name = "created_at")
  @CreationTimestamp
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  private LocalDateTime updatedAt;


}
