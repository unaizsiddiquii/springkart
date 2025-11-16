package com.unaiz.springkart.controller;

import com.unaiz.springkart.config.AppConstants;
import com.unaiz.springkart.dto.CategoryDTO;
import com.unaiz.springkart.dto.CategoryResponse;
import com.unaiz.springkart.entity.Category;
import com.unaiz.springkart.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/public/categories")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO savedCategory = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    //get all
    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> findAllCategory(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_CATEGORIES_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {
        CategoryResponse categoryList = categoryService.findAllCategory(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(categoryList, HttpStatus.OK);
    }

    //get by id
    @GetMapping("/public/categories/{categoryId}")
    public ResponseEntity<Category> findCategoryById(@PathVariable Long categoryId) {
        Category category = categoryService.findCategoryById(categoryId);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    //get by name
    @GetMapping("/public/categories/categoryName/{categoryName}")
    public ResponseEntity<Category> findCategoryByName(@PathVariable String categoryName) {
        Category category = categoryService.findCategoryByName(categoryName);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PutMapping("/public/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable Long categoryId) {
        CategoryDTO savedCategoryDTO = categoryService.updateCategory(categoryDTO, categoryId);
        return new ResponseEntity<>(savedCategoryDTO, HttpStatus.OK);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryId) {
        CategoryDTO categoryDTO = categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
    }

}
