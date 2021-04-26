package ru.lazarev.online_store.model.product;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "promotional_events")
public class PromotionalEvents {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  private String eventsTitle;
  private boolean actual;
  private String discount;
  private Long productId;
  private Long categoryId;

  @Column(name = "expiration_date")
  @UpdateTimestamp
  private LocalDateTime expirationDate;

  @Column(name = "created_at")
  @CreationTimestamp
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  private LocalDateTime updatedAt;



}
