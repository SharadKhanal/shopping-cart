package com.khanalsharad.dailyshoppingcart.service.order;

import com.khanalsharad.dailyshoppingcart.enums.OrderStatus;
import com.khanalsharad.dailyshoppingcart.exception.ResourceNotFoundException;
import com.khanalsharad.dailyshoppingcart.model.Cart;
import com.khanalsharad.dailyshoppingcart.model.Order;
import com.khanalsharad.dailyshoppingcart.model.OrderItem;
import com.khanalsharad.dailyshoppingcart.model.Product;
import com.khanalsharad.dailyshoppingcart.repo.OrderRepository;
import com.khanalsharad.dailyshoppingcart.repo.ProductRepository;
import com.khanalsharad.dailyshoppingcart.service.cart.CartService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;

    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository, CartService cartService) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.cartService = cartService;
    }

    @Override
    public Order placeOrder(Long userId, List<OrderItem> orderItem) {
        Cart cart = cartService.getCartByUserId(userId);
        Order order = createOrder(cart);
        List<OrderItem> orderItems = createOrderItems(order,cart);
        order.setOrderItems(new HashSet<>(orderItems));
        order.setTotalOrderPrice(calculateTotalPrice(order));
        Order orderSaved = orderRepository.save(order);
        cartService.clearCart(cart.getId());
        return orderSaved;
    }

    private Order createOrder(Cart cart){
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDateTime.now());
        return  order;
    }

    private List<OrderItem> createOrderItems(Order order, Cart cart) {
        return cart.getCartItems().stream().map(cartItem->{
            Product product = cartItem.getProduct();
            product.setInventory(product.getInventory()- cartItem.getQuantity());

            productRepository.save(product);
            return new OrderItem(
                    order,
                    product,
                    cartItem.getQuantity(),
                    cartItem.getUnitPrice());
        }).toList();
    }
    @Override
    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(()-> new ResourceNotFoundException("Order not found"));
    }

    private BigDecimal calculateTotalPrice(Order order) {
        return order.getOrderItems()
                .stream().
                map(item -> item.getPrice()
                        .multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }
     @Override
    public List<Order> getAllUserOrders(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}
