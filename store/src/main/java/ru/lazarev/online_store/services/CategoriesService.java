package ru.lazarev.online_store.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lazarev.online_store.model.product.Categories;
import ru.lazarev.online_store.repositories.CategoriesRepository;
import ru.lazarev.online_store.soap.categories.CategoriesSoap;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class CategoriesService {
    private final CategoriesRepository categoryRepository;

    public static final Function<Categories, CategoriesSoap> functionEntityToSoap = ce -> {
        CategoriesSoap c = new CategoriesSoap();
        c.setTitle(c.getTitle());
        ce.getProducts().stream().map(ProductService.functionEntityToSoap).forEach(s -> c.getProducts().add(s));
        return c;
    };

    public CategoriesSoap getByTitle(String title) {
        return categoryRepository.findByTitle(title).map(functionEntityToSoap).get();
    }
}
