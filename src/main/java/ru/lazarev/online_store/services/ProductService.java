package ru.lazarev.online_store.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.lazarev.online_store.dto.ProductDto;
import ru.lazarev.online_store.model.product.Product;
import ru.lazarev.online_store.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    final private ProductRepository productRepository;

    public Optional<Product> findProductById(Long id) {
        return productRepository.findById(id);
    }

    public Optional<ProductDto> findProductDtoById(Long id) {
        return productRepository.findById(id)
                .map(ProductDto::new);
    }

    public List<ProductDto> findAll() {
        return productRepository.findAll().stream().map(ProductDto::new).collect(Collectors.toList());
    }
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public Page<ProductDto> findAll(Specification<Product> spec, int page, int pageSize) {
        return productRepository.findAll(spec, PageRequest.of(page - 1, pageSize)).map(ProductDto::new);
    }


    public static final Function<Product, ru.lazarev.online_store.soap.products.Product> functionEntityToSoap = pe -> {
        ru.lazarev.online_store.soap.products.Product p = new ru.lazarev.online_store.soap.products.Product();
        p.setId(pe.getId());
        p.setTitle(pe.getTitle());
        p.setPrice(Integer.parseInt(pe.getPrice().toString()));
        p.setCategory(pe.getCategories().stream().findFirst().toString());
        return p;
    };

    public List<ru.lazarev.online_store.soap.products.Product> getAllSoapProducts() {
        return productRepository.findAll().stream().map(functionEntityToSoap).collect(Collectors.toList());
    }

    public ru.lazarev.online_store.soap.products.Product getByName(String name) {
        return productRepository.findByName(name).map(functionEntityToSoap).get();
    }
}
