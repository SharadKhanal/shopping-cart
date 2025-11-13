package com.khanalsharad.dailyshoppingcart.repo;

import com.khanalsharad.dailyshoppingcart.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    void deleteAllByCartId(Long id);
}
