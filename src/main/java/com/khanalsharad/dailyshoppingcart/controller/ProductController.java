package com.khanalsharad.dailyshoppingcart.controller;

import com.khanalsharad.dailyshoppingcart.dto.ProductDto;
import com.khanalsharad.dailyshoppingcart.dto.ProductUpdateDto;
import com.khanalsharad.dailyshoppingcart.exception.ProductNotFoundException;
import com.khanalsharad.dailyshoppingcart.model.Product;
import com.khanalsharad.dailyshoppingcart.response.ApiResponse;
import com.khanalsharad.dailyshoppingcart.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody ProductDto productDto) {
      try {
          Product product = productService.addProduct(productDto);
          return ResponseEntity.ok().body(new ApiResponse("Successfully added product", product));
      } catch (Exception e){
          return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
      }
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Long productId, @RequestBody ProductUpdateDto productUpdateDto) {
        try {
                Product updatedProduct = productService.updateProduct(productUpdateDto,productId);
                return ResponseEntity.ok().body(new ApiResponse("Successfully updated product", updatedProduct));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/getProductById/{productId}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable("productId") Long productId) {
        try {
            Product product = productService.getProductById(productId);
            return ResponseEntity.ok(new ApiResponse("Success", product));
        }catch (ProductNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok().body(new ApiResponse("success",products ));
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable("productId") Long productId) {
        try {
            productService.deleteProduct(productId);
            return ResponseEntity.ok().body(new ApiResponse("Successfully deleted product", productId));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
