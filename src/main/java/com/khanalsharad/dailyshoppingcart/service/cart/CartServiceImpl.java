package com.khanalsharad.dailyshoppingcart.service.cart;

import com.khanalsharad.dailyshoppingcart.exception.ResourceNotFoundException;
import com.khanalsharad.dailyshoppingcart.model.Cart;
import com.khanalsharad.dailyshoppingcart.repo.CartItemRepository;
import com.khanalsharad.dailyshoppingcart.repo.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public Cart getCart(Long id) {
        Cart cart= cartRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Cart not found"));

        BigDecimal totalAmount = cart.getTotalPrice();
        cart.setTotalPrice(totalAmount);
        return cartRepository.save(cart);


    }

//    @Override
//    public void clearCart(Long id) {
//    Cart cart = getCart(id);
//    cartItemRepository.deteteAllByCartId(id);
//    cart.getCartItems().clear();
//    cartRepository.deleteById(id);
//    }
     @Override
     @Transactional
    public void clearCart(Long id) {
        Cart cart = getCart(id); // loads managed entity
        cartRepository.delete(cart); // ✅ triggers cascade delete
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getCart(id);
        return cart.getTotalPrice();
    }
}
