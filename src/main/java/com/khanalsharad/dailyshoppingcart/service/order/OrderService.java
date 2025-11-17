package com.khanalsharad.dailyshoppingcart.service.order;

import com.khanalsharad.dailyshoppingcart.model.Order;
import com.khanalsharad.dailyshoppingcart.model.OrderItem;

import java.util.List;

public interface OrderService {

    Order placeOrder(Long userId, List<OrderItem> orderItem);

    Order getOrder(Long orderId);

    List<Order> getAllUserOrders(Long userId);
}
