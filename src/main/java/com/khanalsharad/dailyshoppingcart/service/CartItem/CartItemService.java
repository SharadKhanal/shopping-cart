package com.khanalsharad.dailyshoppingcart.service.CartItem;

import com.khanalsharad.dailyshoppingcart.model.CartItem;

public interface CartItemService {

    void  addCartItem(Long cartId, Long productId, int quantity );

    void removeCartItem(Long cartId, Long productId );

    void updateItemQuantity(Long cartId, Long productId, int quantity);

}
