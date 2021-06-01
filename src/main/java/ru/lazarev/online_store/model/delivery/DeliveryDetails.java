package ru.lazarev.online_store.model.delivery;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "delivery_details")
public class DeliveryDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private Long orderId;
    private String phone;
    private String address;

    @Column(name = "delivery_date")
    private LocalDateTime deliveryDate;

    private String price;
//    private int statusDeliveryId;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;


}
