package com.khanalsharad.dailyshoppingcart.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal totalPrice = BigDecimal.ZERO;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> cartItems = new HashSet<>();

    @OneToOne
    @JoinColumn(name= "user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public Set<CartItem> getCartItems() {
        return cartItems;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setCartItems(Set<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public void addCartItem(CartItem cartItem) {
      cartItems.add(cartItem);
      cartItem.setCart(this);
      updateTotalPrice();
    }

    public void removeCartItem(CartItem cartItem) {
        this.cartItems.remove(cartItem);
          cartItem.setCart(null);
          updateTotalPrice();

    }

    private  void updateTotalPrice() {
      this.totalPrice = cartItems.stream().map(item->{
          BigDecimal unitPrice = item.getUnitPrice();
          if(unitPrice == null){
              return BigDecimal.ZERO;
          }
          return unitPrice.multiply(BigDecimal.valueOf(item.getQuantity()));
      }).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
