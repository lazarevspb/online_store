package ru.lazarev.online_store.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lazarev.online_store.model.Product;
import ru.lazarev.online_store.repositories.ProductRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    final private ProductRepository productRepository;

    public Optional<Product> findById(Long id) {
       return productRepository.findById(id);
    }

}
