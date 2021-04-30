package ru.lazarev.online_store.model.storage;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "storage")
public class Storage {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  private Long productId;
  private Integer quantity;
  private Integer storageDetailsId;
  private Integer availableQuantity;

  @Column(name = "date_of_delivery")
  @UpdateTimestamp
  private LocalDateTime dateOfDelivery;

  @Column(name = "expected_delivery_date")
  @UpdateTimestamp
  private LocalDateTime expectedDeliveryDate;

  @Column(name = "updated_at")
  @UpdateTimestamp
  private LocalDateTime updatedAt;
}
