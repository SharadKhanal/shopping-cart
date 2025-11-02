package com.khanalsharad.dailyshoppingcart.service.category;

import com.khanalsharad.dailyshoppingcart.model.Category;

import java.util.List;

public interface CategoryService {

    Category createCategory(Category category);

    Category updateCategory(Category category, Long categoryId);

    List<Category> findAllCategory();

    Category findCategoryById(Long id);

    Category findCategoryByName(String name);

    void deleteCategoryById(Long id);

}
