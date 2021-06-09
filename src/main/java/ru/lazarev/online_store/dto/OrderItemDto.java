package ru.lazarev.online_store.dto;

import lombok.Data;
import ru.lazarev.online_store.model.order.OrderItem;

import java.math.BigDecimal;

@Data
public class OrderItemDto {
    private Long productId;
    private String productTitle;
    private BigDecimal pricePerProduct;
    private BigDecimal price;
    private Integer quantity;

    public OrderItemDto(OrderItem orderItem) {
//        this.productId = orderItem.getProduct().getId();
        this.productId = orderItem.getProductId();
        this.productTitle = orderItem.getTitle();
        this.pricePerProduct = orderItem.getPricePerProduct();
        this.price = orderItem.getPrice();
        this.quantity = orderItem.getQuantity();
    }
}
