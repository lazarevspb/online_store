package ru.lazarev.online_store.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.lazarev.online_store.model.product.Product;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ProductDto {
    private Long id;
    private String title;
    private BigDecimal price;

    public ProductDto(Product product) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.price = product.getPrice();
    }
}
