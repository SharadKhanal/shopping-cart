package com.khanalsharad.dailyshoppingcart.service.product;

import com.khanalsharad.dailyshoppingcart.model.Product;

import java.util.List;

public interface ProductService {

    Product addProduct(Product product);

    Product getProductById(Long id);

    void updateProduct(Product product, Long productId);

    void deleteProduct(Long id);

    List<Product> getAllProducts();

    List<Product> getProductsByCategory(Long categoryId);

    List<Product> getProductsByBrand(String brand);

    Product getProductByName(String productName);

    Long countProductsByCategory(Long categoryId);

    Long countProductsByBrand(String brand);
}
