package ru.lazarev.online_store.model.product;

import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private int price;

/*  @Column(name = "status_id") // TODO: 27.04.2021 вызывают ошибку
    private int status_id;

    @Column(name = "details_id")
    private int details_id;

    java.lang.IllegalArgumentException:
    Can not set int field ru.lazarev.online_store.model.product.Product.details_id
    to null value
    */

    @Column(name = "category_id")
    private int category_id;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
