package ru.lazarev.online_store.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.lazarev.online_store.model.product.Product;
import ru.lazarev.online_store.repositories.ProductRepository;
import ru.lazarev.online_store.services.ProductService;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;

//@SpringBootTest(value = "classpath:private.properties")
@SpringBootTest(classes = ProductService.class)
public class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @MockBean
    ProductRepository productRepository;

    @Test
    public void testGetProduct() {
        Product mockProduct = new Product();
        mockProduct.setTitle("mockProduct");
        mockProduct.setPrice(BigDecimal.valueOf(555));
        mockProduct.setId(111L);

        Mockito
                .doReturn(Optional.of(mockProduct))
                .when(productRepository)
                .findById(111L);

        Product p = productService.findProductById(111L).get();
        Mockito.verify(productRepository, Mockito.times(1))
                .findById(ArgumentMatchers.eq(111L));
        Assertions.assertEquals("mockProduct", p.getTitle());

    }

    @Test
    public void findProductByIdTest() {
        Assertions.assertThrows(NoSuchElementException.class,
                () -> productService.findProductById(500L).get());
    }

}

