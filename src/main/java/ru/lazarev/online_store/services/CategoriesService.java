package ru.lazarev.online_store.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lazarev.online_store.model.product.Categories;
import ru.lazarev.online_store.repositories.CategoriesRepository;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class CategoriesService {
    private final CategoriesRepository categoryRepository;

    public static final Function<Categories, ru.lazarev.online_store.soap.categories.Categories> functionEntityToSoap = ce -> {
        ru.lazarev.online_store.soap.categories.Categories c = new ru.lazarev.online_store.soap.categories.Categories();
        c.setTitle(c.getTitle());
        ce.getProducts().stream().map(ProductService.functionEntityToSoap).forEach(s -> c.getProducts().add(s));
        return c;
    };

    public ru.lazarev.online_store.soap.categories.Categories getByTitle(String title) {
        return categoryRepository.findByTitle(title).map(functionEntityToSoap).get();
    }
}
