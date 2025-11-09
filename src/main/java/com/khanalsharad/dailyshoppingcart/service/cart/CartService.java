package com.khanalsharad.dailyshoppingcart.service.cart;

import com.khanalsharad.dailyshoppingcart.model.Cart;

import java.math.BigDecimal;

public interface CartService {

    public Cart getCart(Long id );

    public void clearCart(Long id );

    public BigDecimal getTotalPrice(Long id );
}
