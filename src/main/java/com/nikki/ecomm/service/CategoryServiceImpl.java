package com.nikki.ecomm.service;

import com.nikki.ecomm.exceptions.APIException;
import com.nikki.ecomm.exceptions.ResourceNotFound;
import com.nikki.ecomm.models.Category;
import com.nikki.ecomm.payload.CategoryDTO;
import com.nikki.ecomm.payload.CategoryResponseDTO;
import com.nikki.ecomm.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.parser.Part;
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
    public CategoryResponseDTO getCategories(Integer pageNumber, Integer pageSize,String sortBy, String sortDir) {

        Sort sortByandOrder = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByandOrder);
        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);

        List<Category> categories = categoryPage.getContent();
        if (categories.isEmpty()) {
            throw new APIException("No categories found");
        }
        List<CategoryDTO> categoryDTOS = categories.stream().map(category -> modelMapper.map(category, CategoryDTO.class)).collect(Collectors.toList());
        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
        categoryResponseDTO.setCategoryDTOList(categoryDTOS);
        categoryResponseDTO.setPageNumber(categoryPage.getNumber());
        categoryResponseDTO.setPageSize(categoryPage.getSize());
        categoryResponseDTO.setTotalPages(categoryPage.getTotalPages());
        categoryResponseDTO.setTotalElements(categoryPage.getTotalElements());
        categoryResponseDTO.setLastPage(categoryPage.isLast());
        return categoryResponseDTO;
    }

    @Override
    public CategoryDTO addCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map( categoryDTO, Category.class);
        String name = category.getCategoryName();
        Category fetchedCategory = categoryRepository.findByCategoryName(name);
        if (fetchedCategory != null) {
            throw  new APIException(String.format("Category %s already exists",name) );
        }
        Category savedCategory = categoryRepository.save(category);
        CategoryDTO savedCategoryDTO = modelMapper.map(savedCategory, CategoryDTO.class);
        return savedCategoryDTO;
    }

    @Override
    public CategoryDTO updateCategory(Long categoryId,CategoryDTO categoryDTO) {
        Category fetchedCategory = categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFound("Category","Categoryid",categoryId));
        Category category = modelMapper.map(categoryDTO, Category.class);
        fetchedCategory.setCategoryName(category.getCategoryName());
        Category updatedCategory = categoryRepository.save(fetchedCategory);
        CategoryDTO fetchedCategoryDTO = modelMapper.map(updatedCategory, CategoryDTO.class);
        return fetchedCategoryDTO;

    }

    @Override
    public CategoryDTO deleteCategory(Long categoryid) {
        Category fetchedCategory = categoryRepository.findById(categoryid)
                .orElseThrow(()->new ResourceNotFound("Category","CategoryId",categoryid));
        CategoryDTO deletedCategoryDTO = modelMapper.map(fetchedCategory, CategoryDTO.class);
        categoryRepository.delete(fetchedCategory);
        return deletedCategoryDTO;
    }

}
