package com.khanalsharad.dailyshoppingcart.service.product;

import com.khanalsharad.dailyshoppingcart.dto.ImageDto;
import com.khanalsharad.dailyshoppingcart.dto.ProductDto;
import com.khanalsharad.dailyshoppingcart.dto.ProductUpdateDto;
import com.khanalsharad.dailyshoppingcart.exception.ProductNotFoundException;
import com.khanalsharad.dailyshoppingcart.model.Category;
import com.khanalsharad.dailyshoppingcart.model.Image;
import com.khanalsharad.dailyshoppingcart.model.Product;
import com.khanalsharad.dailyshoppingcart.repo.CategoryRepository;
import com.khanalsharad.dailyshoppingcart.repo.ImageRepository;
import com.khanalsharad.dailyshoppingcart.repo.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;
    private static final Logger log = Logger.getLogger(ProductServiceImpl.class.getName());
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, ImageRepository imageRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.imageRepository = imageRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Product addProduct(ProductDto productDto) {
        // first check category if exist save the product if  not save category and then product
        log.info("Adding product {}"+ productDto);
        Category category = Optional.ofNullable(categoryRepository.findByName(productDto.getCategory())).orElseGet(() ->
        {
            Category category1 = new Category(productDto.getCategory());
            return categoryRepository.save(category1);
        });

        productDto.setCategory(category.getName());
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
        log.info("Getting product by id {}::" + id);
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
    public List<ProductDto> getAllProducts() {
        log.info("Getting all products");
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::getProductResponse).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByCategory(Long categoryId) {
        log.info("Getting products by category {}"+ categoryId);
        List<Product> products = productRepository.findByCategoryId(categoryId);
        return products.stream().map(this::getProductResponse).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByBrand(String brand) {
        log.info("Getting products by brand {}"+ brand);
        List<Product> products = productRepository.findByBrand(brand);
        return products.stream().map(this::getProductResponse).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByCategoryAndBrand(String categoryId, String brand) {
        List<Product>products = productRepository.findByCategoryNameAndBrand(categoryId, brand);
        return products.stream().map(this::getProductResponse).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByBrandAndName(String brand, String name) {
        List<Product> products = productRepository.findByBrandAndName(brand, name);
        return products.stream().map(this::getProductResponse).collect(Collectors.toList());
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

    public ProductDto getProductResponse(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setBrand(product.getBrand());
        productDto.setPrice(product.getPrice());
        productDto.setInventory(product.getInventory());
        productDto.setDescription(product.getDescription());
        productDto.setCategory(product.getCategory().getName());
        productDto.setImages(converToImageDto(product.getId()));
        return productDto;
    }
//
    public List<ImageDto> converToImageDto(Long productId) {
        List<Image> images = imageRepository.findByProductId(productId);
        List<ImageDto> imageDtos = new ArrayList<>();

        for (Image image : images) {
            ImageDto imageDto = new ImageDto();
            imageDto.setImageId(image.getId());
            imageDto.setImageName(image.getFileName());
            imageDto.setDownloadUrl(image.getDownloadUrl());
            imageDtos.add(imageDto);
        }
        return imageDtos;
    }

//    public List<ImageDto> converToImageDto(Long productId) {
//        return imageRepository.findByProductId(productId)
//                .stream()
//                .map(image -> new ImageDto(image.getId(), image.getFileName(), image.getDownloadUrl()))
//                .collect(Collectors.toList());
//    }


}
