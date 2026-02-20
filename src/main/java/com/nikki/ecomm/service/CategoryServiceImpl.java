package com.nikki.ecomm.service;

import com.nikki.ecomm.exceptions.APIException;
import com.nikki.ecomm.exceptions.ResourceNotFound;
import com.nikki.ecomm.models.Category;
import com.nikki.ecomm.payload.CategoryDTO;
import com.nikki.ecomm.payload.CategoryResponseDTO;
import com.nikki.ecomm.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public CategoryResponseDTO getCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            throw new APIException("No categories found");
        }
        List<CategoryDTO> categoryDTOS = categories.stream().map(category -> modelMapper.map(category, CategoryDTO.class)).collect(Collectors.toList());
        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
        categoryResponseDTO.setCategoryDTOList(categoryDTOS);
        return categoryResponseDTO;
    }

    @Override
    public String addCategory(CategoryDTO categoryDTO) {
        String name = categoryDTO.getCategoryName();
        Category fetchedCategory = categoryRepository.findByCategoryName(name);
        if (fetchedCategory != null) {
            throw  new APIException(String.format("Category %s already exists",name) );
        }
        Category category = modelMapper.map( categoryDTO, Category.class);
        System.out.println(category.toString());
        categoryRepository.save(category);
        return "Category added sucessfully!";
    }

    @Override
    public String updateCategory(Long categoryId,Category category) {
        Category fetchedCategory = categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFound("Category","Categoryid",categoryId));
        fetchedCategory.setCategoryName(category.getCategoryName());
        categoryRepository.save(fetchedCategory);
        return "Category updated sucessfully!";

    }

    @Override
    public String deleteCategory(Long categoryid) {
        Category fetchedCategory = categoryRepository.findById(categoryid)
                .orElseThrow(()->new ResourceNotFound("Category","CategoryId",categoryid));
        categoryRepository.delete(fetchedCategory);
        return "Category deleted sucessfully!";
    }

}
