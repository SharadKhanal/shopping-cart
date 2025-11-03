package com.khanalsharad.dailyshoppingcart.service.category;

import com.khanalsharad.dailyshoppingcart.exception.AlreadyExistException;
import com.khanalsharad.dailyshoppingcart.exception.CategoryNotFoundException;
import com.khanalsharad.dailyshoppingcart.model.Category;
import com.khanalsharad.dailyshoppingcart.repo.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category createCategory(Category category) {
        log.info("Creating category: {}", category);
        return Optional.of(category).filter(c -> !categoryRepository.existsByName(c.getName()))
                .map(categoryRepository::save).orElseThrow(() ->
                        new AlreadyExistException("Category already exist with name{{}}" + category.getName()));
    }

    @Override
    public Category updateCategory(Category category, Long id) {
//            return categoryRepository.findById(id)
//                    .map(existingCategory -> {
//                        existingCategory.setName(category.getName());
//                        return categoryRepository.save(existingCategory);
//                    })
//                    .orElseThrow(() -> new CategoryNotFoundException("Category not found"));


        return Optional.ofNullable(findCategoryById(id)).map(existingCategory -> {
            existingCategory.setName(category.getName());
            return categoryRepository.save(existingCategory);
        }).orElseThrow(() -> new CategoryNotFoundException("Category not found"));
    }

    @Override
    public List<Category> findAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found !"));
    }

    @Override
    public Category findCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.findById(id).ifPresentOrElse(categoryRepository::delete,
                () -> {
                    throw new CategoryNotFoundException("Category not exist with id{{}}" + id);
                });
    }
}
