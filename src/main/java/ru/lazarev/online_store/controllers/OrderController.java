package ru.lazarev.online_store.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.lazarev.online_store.dto.OrderDto;
import ru.lazarev.online_store.exception.ResourceNotFoundException;
import ru.lazarev.online_store.model.order.Order;
import ru.lazarev.online_store.model.users.User;
import ru.lazarev.online_store.services.CartService;
import ru.lazarev.online_store.services.OrderService;
import ru.lazarev.online_store.services.UserService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;
    private final CartService cartService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto createOrderFromCart(Principal principal, @RequestParam String address) {
        Order order = orderService.createFromUserCart(principal.getName(), address);
        User user = userService.findByUsername(principal.getName()).orElseThrow();
        cartService.clearCart(user.getId());
        return new OrderDto(order);
    }

    @GetMapping("/{id}")
    public OrderDto getOrderById(@PathVariable Long id) {
        System.out.println("id = " + id);
        Order order = orderService
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return new OrderDto(order);
    }

    @GetMapping
    public List<OrderDto> getCurrentUserOrders(Principal principal) {
        return orderService.findAllByOwner(principal.getName()).stream().map(OrderDto::new).collect(Collectors.toList());
    }
}
