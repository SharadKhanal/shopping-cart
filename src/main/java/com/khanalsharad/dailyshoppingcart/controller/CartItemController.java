package com.khanalsharad.dailyshoppingcart.controller;

import com.khanalsharad.dailyshoppingcart.exception.ResourceNotFoundException;
import com.khanalsharad.dailyshoppingcart.response.ApiResponse;
import com.khanalsharad.dailyshoppingcart.service.CartItem.CartItemService;
import com.khanalsharad.dailyshoppingcart.service.CartItem.CartItemServiceImpl;
import com.khanalsharad.dailyshoppingcart.service.cart.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("api/v1/cartItem")
public class CartItemController {

    private final CartItemService cartItemService;
    private final CartService cartService;

    public CartItemController(CartItemService cartItemService, CartItemServiceImpl cartItemServiceImpl, CartService cartService) {
        this.cartItemService = cartItemService;
        this.cartService = cartService;
    }
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestParam (required = false) Long cartId,
                                                   @RequestParam Long productId,
                                                   @RequestParam Integer quantity) {
        try {
            if (cartId == null){
              cartId =  cartService.initializeNewCart();
            }
            cartItemService.addCartItem(cartId, productId, quantity);
            return ResponseEntity.ok(new ApiResponse("Successfully added item to cart.",null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/removeCartItem")
    public ResponseEntity<ApiResponse> removeCartItem(@RequestParam Long cartId,
                                                      @RequestParam Long productId) {
       try {
           cartItemService.removeCartItem(cartId, productId);
           return ResponseEntity.ok(new ApiResponse("Successfully removed item from cart.", null));
       }catch (ResourceNotFoundException e) {
           return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
       }
    }

    @PutMapping("/updateItemQuantity")
    public ResponseEntity<ApiResponse> updateItemQuantity(@RequestParam Long cartId,
                                                          @RequestParam Long productId,
                                                          @RequestParam Integer quantity) {
        try{
            cartItemService.updateItemQuantity(cartId, productId, quantity);
            return ResponseEntity.ok(new ApiResponse("Successfully updated quantity to cart.",null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
}
