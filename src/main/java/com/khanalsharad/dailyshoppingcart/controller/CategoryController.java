package com.khanalsharad.dailyshoppingcart.controller;

import com.khanalsharad.dailyshoppingcart.exception.AlreadyExistException;
import com.khanalsharad.dailyshoppingcart.exception.CategoryNotFoundException;
import com.khanalsharad.dailyshoppingcart.model.Category;
import com.khanalsharad.dailyshoppingcart.response.ApiResponse;
import com.khanalsharad.dailyshoppingcart.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> createCategory(@RequestBody Category category) {
        try {
            Category category1 = categoryService.createCategory(category);
            return ResponseEntity.ok().body(new ApiResponse("Category created", category1));
        } catch (AlreadyExistException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories() {
        try {
            List<Category> categories = categoryService.findAllCategory();
            return ResponseEntity.ok().body(new ApiResponse("All categories", categories));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/getById/{categoryId}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable("categoryId") Long categoryId) {
        try {
            Category category = categoryService.findCategoryById(categoryId);
            return ResponseEntity.ok().body(new ApiResponse("Category found", category));
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/getByName/{categoryName}")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable("categoryName") String categoryName) {
        try {
            Category category = categoryService.findCategoryByName(categoryName);
            return ResponseEntity.ok().body(new ApiResponse("Category found", category));
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") Long categoryId) {
        try {
            categoryService.deleteCategoryById(categoryId);
            return ResponseEntity.ok().body(new ApiResponse("Category deleted", null));
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
