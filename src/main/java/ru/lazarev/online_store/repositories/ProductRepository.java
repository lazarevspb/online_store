package ru.lazarev.online_store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lazarev.online_store.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
