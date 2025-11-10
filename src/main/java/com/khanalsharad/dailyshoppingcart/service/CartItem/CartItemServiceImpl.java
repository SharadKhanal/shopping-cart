package com.khanalsharad.dailyshoppingcart.service.CartItem;

import com.khanalsharad.dailyshoppingcart.model.Cart;
import com.khanalsharad.dailyshoppingcart.model.CartItem;
import com.khanalsharad.dailyshoppingcart.model.Product;
import com.khanalsharad.dailyshoppingcart.repo.CartItemRepository;
import com.khanalsharad.dailyshoppingcart.repo.CartRepository;
import com.khanalsharad.dailyshoppingcart.service.cart.CartService;
import com.khanalsharad.dailyshoppingcart.service.product.ProductService;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.math.BigDecimal;

@Service
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ProductService productService;
    private final CartService cartService;
    private final CartRepository cartRepository;

    public CartItemServiceImpl(CartItemRepository cartItemRepository, ProductService productService, CartService cartService, CartRepository cartRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productService = productService;
        this.cartService = cartService;
        this.cartRepository = cartRepository;
    }

    @Override
    public void addCartItem(Long cartId, Long productId, int quantity) {

        // 1. Get the cart
        // 2. Get the product
        // 3. Check if item already exist in the cart
        // 4. if yes, then increase the quantity of item with the requested one
        // 5. if no, initiate the new cart item

        Cart cart =  cartService.getCart(cartId);
        Product product = productService.getProductById(productId);
        CartItem cartItem =  cart.getCartItems()
                .stream().filter(items-> items.getProduct().getId().equals(productId))
                .findFirst().orElse(new CartItem());

        if(cartItem.getId()== null) {
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        cartItem.setTotalPrice();
        cart.addCartItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void removeCartItem(Long cartId, Long productId) {
     Cart cart =  cartService.getCart(cartId);
     CartItem cartItem =  getCartItem(cartId,productId);
     cart.removeCartItem(cartItem);

     cartRepository.save(cart);
    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
       Cart cart =  cartService.getCart(cartId);
       cart.getCartItems()
               .stream().filter(items-> items.getProduct().getId().equals(productId))
               .findFirst()
               .ifPresent(items->{
                   items.setQuantity(quantity);
                   items.setUnitPrice(items.getProduct().getPrice());
                   items.setTotalPrice();
                       });
        BigDecimal totalPrice = cart.getTotalPrice();
        cart.setTotalPrice(totalPrice);
        cartRepository.save(cart);
    }

    public CartItem getCartItem(Long cartId, Long productId) {
        Cart cart =  cartService.getCart(cartId);
        return cart.getCartItems()
                .stream().
                filter(items->items.getProduct().getId().equals(productId))
                .findFirst().orElseThrow(() -> new ResolutionException("CartItem not found"));

    }

}
