package ru.lazarev.online_store.repositories;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.lazarev.online_store.model.cart.Cart;

@Repository
public interface CartRepository extends CrudRepository<Cart, Long> {
//    Optional<Cart> findCartById(Long id);
}
