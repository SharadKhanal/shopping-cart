package com.khanalsharad.dailyshoppingcart.service.product;

import com.khanalsharad.dailyshoppingcart.dto.ProductDto;
import com.khanalsharad.dailyshoppingcart.dto.ProductUpdateDto;
import com.khanalsharad.dailyshoppingcart.model.Product;

import java.util.List;

public interface ProductService {

    Product addProduct(ProductDto product);

    Product getProductById(Long id);

    Product updateProduct(ProductUpdateDto updatedProductDto, Long productId);

    void deleteProduct(Long id);

    List<ProductDto> getAllProducts();

    List<ProductDto> getProductsByCategory(Long categoryId);

    List<ProductDto> getProductsByBrand(String brand);

    List<ProductDto> getProductsByCategoryAndBrand(String categoryId, String brand);

    List<ProductDto> getProductsByBrandAndName(String brand, String name);

    Product getProductByName(String productName);

    Long countProductsByCategory(Long categoryId);

    Long countProductsByBrand(String brand);

    Long countProductByBrandAndName(String brand, String name);
}
