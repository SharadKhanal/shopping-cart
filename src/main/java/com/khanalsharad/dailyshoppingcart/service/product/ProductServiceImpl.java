package com.khanalsharad.dailyshoppingcart.service.product;

import com.khanalsharad.dailyshoppingcart.dto.ProductDto;
import com.khanalsharad.dailyshoppingcart.dto.ProductUpdateDto;
import com.khanalsharad.dailyshoppingcart.exception.ProductNotFoundException;
import com.khanalsharad.dailyshoppingcart.model.Category;
import com.khanalsharad.dailyshoppingcart.model.Product;
import com.khanalsharad.dailyshoppingcart.repo.CategoryRepository;
import com.khanalsharad.dailyshoppingcart.repo.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private static final Logger log = Logger.getLogger(ProductServiceImpl.class.getName());
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product addProduct(ProductDto productDto) {
        // first check category if exist save the product if  not save category and then product
        log.info("Adding product {}"+ productDto);
        Category category = Optional.ofNullable(categoryRepository.findByName(productDto.getCategory().getName())).orElseGet(() ->
        {
            Category category1 = new Category(productDto.getCategory().getName());
            return categoryRepository.save(category1);
        });

        productDto.setCategory(category);
        return productRepository.save(createProduct(productDto, category));

    }

    private Product createProduct(ProductDto product, Category category) {

        return new Product(
                product.getName(),
                product.getBrand(),
                product.getPrice(),
                product.getInventory(),
                product.getDescription(),
                category
        );
    }

    @Override
    public Product getProductById(Long id) {
        log.info("Getting product by id {}"+ id);
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    @Override
    public Product updateProduct(ProductUpdateDto updatedProductDto, Long productId) {
        log.info("Updating product with id {}"+ productId);
        return productRepository.findById(productId).map(
                        existingProduct -> updateExistingProduct(existingProduct, updatedProductDto)
                ).map(productRepository::save)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    private Product updateExistingProduct(Product existingProduct, ProductUpdateDto updatedProductDto) {
        existingProduct.setName(updatedProductDto.getName());
        existingProduct.setName(updatedProductDto.getName());
        existingProduct.setBrand(updatedProductDto.getBrand());
        existingProduct.setInventory(updatedProductDto.getInventory());
        existingProduct.setPrice(updatedProductDto.getPrice());
        existingProduct.setDescription(updatedProductDto.getDescription());

        Category category = categoryRepository.findByName(updatedProductDto.getCategory().getName());
        existingProduct.setCategory(category);
        return existingProduct;
    }

    @Override
    public void deleteProduct(Long id) {
        log.info("Deleting product with id {}"+ id);
        productRepository.findById(id).ifPresentOrElse(productRepository::delete,
                () -> {
                    throw new ProductNotFoundException("Product not found");
                });
    }

    @Override
    public List<Product> getAllProducts() {
        log.info("Getting all products");
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(Long categoryId) {
        log.info("Getting products by category {}"+ categoryId);
        return productRepository.findByCategoryId(categoryId);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String categoryId, String brand) {
        return productRepository.findByCategoryNameAndBrand(categoryId, brand);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Product getProductByName(String productName) {
        return productRepository.findByName(productName);
    }

    @Override
    public Long countProductsByCategory(Long categoryId) {
        return productRepository.countProductsByCategory(categoryId);
    }

    @Override
    public Long countProductsByBrand(String brand) {
        return productRepository.countByBrand(brand);
    }

    @Override
    public Long countProductByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }
}
