package com.khanalsharad.dailyshoppingcart.repo;

import com.khanalsharad.dailyshoppingcart.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategoryId(Long categoryId);

    List<Product> findByBrand(String brandName);

    Product findByName(String name);
    @Query("SELECT COUNT(p) from Product  p where p.category = :categoryId")
    Long countProductsByCategory(@Param("categoryId") Long categoryId);

    Long countByBrandAndName(String brand, String name);

    List<Product> findByCategoryNameAndBrand(String categoryId, String brand);

    List<Product> findByBrandAndName(String brand, String name);

    Long countByBrand(String brand);
}
