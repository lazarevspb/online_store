package ru.lazarev.online_store.model.cart;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;
import ru.lazarev.online_store.model.product.Product;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@RedisHash("cart_item")
public class CartItem {

    @Id
    private Long id;
    @Indexed
    private Cart cart;
    private Product product;
    private String title;
    private Integer quantity;
    private BigDecimal pricePerProduct;
    private BigDecimal price;

    public CartItem(Product product) {
        this.product = product;
        this.title = product.getTitle();
        this.quantity = 1;
        this.pricePerProduct = product.getPrice();
        this.price = pricePerProduct;
    }


//    public void addProduct(Product product) {
//        this.product = product;
//        this.quantity = 1;
////        this.pricePerProduct = product.getPrice();
//        price = this.pricePerProduct;
//    }
//
//    public void incrementQuantity() {
//        quantity++;
////        price = quantity * pricePerProduct;
//    }
//
//    public void decrementQuantity() {
//        quantity--;
////        price = quantity * pricePerProduct;
//    }


}
