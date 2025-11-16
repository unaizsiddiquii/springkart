package com.unaiz.springkart.service;

import com.unaiz.springkart.dto.CategoryDTO;
import com.unaiz.springkart.dto.CategoryResponse;
import com.unaiz.springkart.entity.Category;

public interface CategoryService {

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    Category findCategoryById(Long id);

    CategoryResponse findAllCategory(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    Category findCategoryByName(String name);

    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long id);

    CategoryDTO deleteCategory(Long id);
}
