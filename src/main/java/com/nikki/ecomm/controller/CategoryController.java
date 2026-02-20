package com.nikki.ecomm.controller;

import com.nikki.ecomm.models.Category;
import com.nikki.ecomm.payload.CategoryDTO;
import com.nikki.ecomm.payload.CategoryResponseDTO;
import com.nikki.ecomm.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponseDTO>  getAllCategories() {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getCategories());
    }

    @PostMapping("/public/categories")
    public ResponseEntity<String> addCategory(@Valid  @RequestBody CategoryDTO categoryDTO) {
        String result = categoryService.addCategory(categoryDTO);
        //System.out.println(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/public/categories/{categoryId}")
    public ResponseEntity<String> updateCategory(@PathVariable("categoryId") Long categoryId, @Valid  @RequestBody Category category) {
            String  result = categoryService.updateCategory(categoryId, category);
            return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/admin/categories/{categoryID}")
    public ResponseEntity<String> deleteCategory(@PathVariable("categoryID") Long categoryID) {

            String result = categoryService.deleteCategory(categoryID);
            return ResponseEntity.status(HttpStatus.OK).body(result);

    }




    
}
