package com.nikki.ecomm.service;

import com.nikki.ecomm.models.Category;
import com.nikki.ecomm.payload.CategoryDTO;
import com.nikki.ecomm.payload.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {

    CategoryResponseDTO getCategories();
    String addCategory(CategoryDTO categoryDTO);
    String updateCategory(Long id, Category category);

    String deleteCategory(Long id);
}
