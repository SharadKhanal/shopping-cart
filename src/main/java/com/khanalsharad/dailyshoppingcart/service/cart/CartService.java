package com.khanalsharad.dailyshoppingcart.service.cart;
import com.khanalsharad.dailyshoppingcart.model.Cart;
import java.math.BigDecimal;

public interface CartService {

     Cart getCart(Long id );

     void clearCart(Long cartId );

     BigDecimal getTotalPrice(Long id );

     Long initializeNewCart();
}
