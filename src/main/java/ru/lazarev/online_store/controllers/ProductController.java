package ru.lazarev.online_store.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import ru.lazarev.online_store.dto.ProductDto;
import ru.lazarev.online_store.exception.ResourceNotFoundException;
import ru.lazarev.online_store.model.product.Product;
import ru.lazarev.online_store.services.ProductService;
import ru.lazarev.online_store.specifications.ProductSpecifications;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/{id}")
    public ProductDto findById(@PathVariable Long id) {
        return productService.findProductDtoById(id).orElseThrow(() -> new ResourceNotFoundException(
                String.format("Продукт с id: %d не найден", id)
        ));
    }

    @PostMapping
    public Product createNewProduct(@RequestBody Product product) {
        product.setId(null);
        return productService.save(product);
    }

    @PutMapping
    public Product updateProduct(@RequestBody Product product) {
        return productService.save(product);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @GetMapping
    public Page<ProductDto> findAllProducts(
            @RequestParam MultiValueMap<String, String> params,
            @RequestParam(name = "p", defaultValue = "1") Integer page) {
        if (page < 1) {
            page = 1;
        }
        return productService.findAll(ProductSpecifications.build(params), page, 4);
    }
}
