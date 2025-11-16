package com.unaiz.springkart.service;

import com.unaiz.springkart.dto.CategoryDTO;
import com.unaiz.springkart.dto.CategoryResponse;
import com.unaiz.springkart.entity.Category;
import com.unaiz.springkart.exception.APIException;
import com.unaiz.springkart.exception.ResourceNotFoundException;
import com.unaiz.springkart.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;


    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        if (categoryRepository.findByCategoryName(categoryDTO.getCategoryName()).isPresent()) {
            throw new APIException("Category with name " + categoryDTO.getCategoryName() + " is already present");
        }

        Category category = modelMapper.map(categoryDTO, Category.class);
        Category newCategory = categoryRepository.save(category);
        return modelMapper.map(newCategory, CategoryDTO.class);
    }

    @Override
    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
    }

    @Override
    public CategoryResponse findAllCategory(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Category> categoryPage = categoryRepository.findAll(pageable);
        if (categoryPage.isEmpty()) {
            throw new APIException("No Category is present.");
        }

        List<CategoryDTO> categoryDTOS = categoryPage.stream().map(category -> modelMapper.map(category, CategoryDTO.class)).toList();
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setPageSize(categoryPage.getSize());
        categoryResponse.setTotalElements(categoryPage.getTotalElements());
        categoryResponse.setTotalPages(categoryPage.getTotalPages());
        categoryResponse.setLastPage(categoryPage.isLast());
        return categoryResponse;
    }

    @Override
    public Category findCategoryByName(String name) {
        return categoryRepository.findByCategoryName(name).orElseThrow(() -> new ResourceNotFoundException("Category", "name", name));
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

        category.setCategoryName(categoryDTO.getCategoryName());
        Category updatedCategory = categoryRepository.save(category);

        return modelMapper.map(updatedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO deleteCategory(Long id) {

        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        categoryRepository.deleteById(id);
        return modelMapper.map(category, CategoryDTO.class);
    }

}
