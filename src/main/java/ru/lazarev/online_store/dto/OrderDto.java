package ru.lazarev.online_store.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.lazarev.online_store.model.order.Order;
import ru.lazarev.online_store.model.order.OrderItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class OrderDto {
    private Long id;
    private String username;
    private String address;
    private BigDecimal totalPrice;
    private List<OrderItemDto> items;
    private String creationDateTime;

    public OrderDto(Order order) {
        this.id = order.getId();
        this.username = order.getOwner().getUsername();
        this.address = order.getAddress();
        this.totalPrice = order.getPrice();
        this.creationDateTime = order.getCreatedAt().toString();
        this.items = new ArrayList<>();
        List<OrderItem> orderItems = order.getOrderItems();
        orderItems.forEach(i -> this.items.add(new OrderItemDto(i)));
    }
}
