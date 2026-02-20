package com.nikki.ecomm.service;

import com.nikki.ecomm.models.Category;
import com.nikki.ecomm.payload.CategoryDTO;
import com.nikki.ecomm.payload.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {

    CategoryResponseDTO getCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    CategoryDTO addCategory(CategoryDTO categoryDTO);

    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);

    CategoryDTO deleteCategory(Long id);
}
