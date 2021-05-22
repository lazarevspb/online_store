package ru.lazarev.online_store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.lazarev.online_store.model.product.Categories;


import java.util.Optional;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Long> {
    @Query("select c from Categories c where c.name = ?1")
    Optional<Categories> findByTitle(String title);
}
