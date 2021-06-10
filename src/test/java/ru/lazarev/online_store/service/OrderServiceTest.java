package ru.lazarev.online_store.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.lazarev.online_store.model.cart.Cart;
import ru.lazarev.online_store.model.order.Order;
import ru.lazarev.online_store.model.users.User;
import ru.lazarev.online_store.repositories.OrderRepository;
import ru.lazarev.online_store.services.CartService;
import ru.lazarev.online_store.services.OrderService;
import ru.lazarev.online_store.services.UserService;


import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;


@SpringBootTest(classes = OrderService.class)
class OrderServiceTest {
    @Autowired
    OrderService orderService;

    @MockBean
    OrderRepository orderRepository;

    @MockBean
    CartService cartService;

    @MockBean
    UserService userService;

    @Test
    void findAllByOwner() {
        User mockUser = new User();
        mockUser.setUsername("mockUser");
        mockUser.setId(100L);
        mockUser.setFirstName("mockUserFirstName");
        mockUser.setEmail("email@mail.com");

        Cart mockCart = new Cart();
        mockCart.setPrice(BigDecimal.valueOf(500));
        mockCart.setOwnerId(100L);

        Order mockOrder = new Order(mockCart, "addressForMockOrder", mockUser);
        mockOrder.setId(1L);

        Mockito
                .doReturn(Arrays.asList(mockOrder))
                .when(orderRepository)
                .findAllByOwnerUsername("mockUser");

        List<Order> list = orderService.findAllByOwner("mockUser");
        Mockito.verify(orderRepository, Mockito.times(1))
                .findAllByOwnerUsername(ArgumentMatchers.eq("mockUser"));

        Assertions.assertEquals("addressForMockOrder", list.get(0).getAddress());
        Assertions.assertEquals(1L, list.get(0).getId());
    }
}
