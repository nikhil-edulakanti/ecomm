package com.nikki.ecomm.controller;

import com.nikki.ecomm.configuration.AppConstants;
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

    @GetMapping("/echo")
    public ResponseEntity<String> displayEcho(@RequestParam(name = "in" ) String input) {
        return ResponseEntity.ok("The input from thw URL is " + input);
    }

    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponseDTO>  getAllCategories(
            @RequestParam(name ="pageNumber",defaultValue = AppConstants.pageNumber, required = false) Integer pageNumber,
            @RequestParam(name ="pageSize", defaultValue = AppConstants.pageSize , required = false) Integer pageSize,
            @RequestParam(name ="sortBy", defaultValue = AppConstants.sortBy, required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = AppConstants.sortDirection, required = false) String sortDir) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getCategories(pageNumber, pageSize, sortBy,sortDir));
    }

    @PostMapping("/public/categories")
    public ResponseEntity<CategoryDTO> addCategory(@Valid  @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO savedCategory = categoryService.addCategory(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }

    @PutMapping("/public/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable("categoryId") Long categoryId, @Valid  @RequestBody CategoryDTO categoryDTO) {
            CategoryDTO updatedCategoryDTO = categoryService.updateCategory(categoryId, categoryDTO);
            return ResponseEntity.status(HttpStatus.OK).body(updatedCategoryDTO);
    }

    @DeleteMapping("/admin/categories/{categoryID}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable("categoryID") Long categoryID) {

            CategoryDTO deletedCategoryDTO = categoryService.deleteCategory(categoryID);
            return ResponseEntity.status(HttpStatus.OK).body(deletedCategoryDTO);

    }




    
}
