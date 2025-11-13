package com.khanalsharad.dailyshoppingcart.controller;

import com.khanalsharad.dailyshoppingcart.exception.ResourceNotFoundException;
import com.khanalsharad.dailyshoppingcart.model.Cart;
import com.khanalsharad.dailyshoppingcart.response.ApiResponse;
import com.khanalsharad.dailyshoppingcart.service.cart.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/getCartById/{cartId}")
    public ResponseEntity<ApiResponse> getCartById(@PathVariable("cartId") Long cartId) {
        try {
            Cart cart = cartService.getCart(cartId);
            return ResponseEntity.ok(new ApiResponse("success", cart));
        }catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/clearCart/{cartId}")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable("cartId") Long cartId) {
        try {
            cartService.clearCart(cartId);
            return ResponseEntity.ok(new ApiResponse("success", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/getTotalPrice/{cartId}")
    public ResponseEntity<ApiResponse> getTotalPrice(@PathVariable("cartId") Long cartId) {
        try {
            BigDecimal totalPrice = cartService.getTotalPrice(cartId);
            return ResponseEntity.ok(new ApiResponse("success", totalPrice));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

}
