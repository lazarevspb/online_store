package ru.lazarev.online_store.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "orders")
public class Orders {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;


  private Long userId;
  private Long orderItemId;
  private int discount;
  private Long promotionalEventId;
  private int totalDiscount;
  private int totalPrice;
  private int tPriceWDiscount;
  private String phone;
  private String address;
  private String deliveryRequired;
  private Long deliveryDetailsId;
  private Long orderStatusId;

  @Column(name = "created_at")
  @CreationTimestamp
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  private LocalDateTime updatedAt;


}
