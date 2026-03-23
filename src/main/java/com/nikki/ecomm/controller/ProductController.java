package com.nikki.ecomm.controller;


import com.nikki.ecomm.configuration.AppConstants;
import com.nikki.ecomm.models.Product;
import com.nikki.ecomm.payload.ProductDTO;
import com.nikki.ecomm.payload.ProductResponseDTO;
import com.nikki.ecomm.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct (@RequestBody Product product, @PathVariable Long categoryId) {

        ProductDTO productDTO = productService.addProduct(categoryId, product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productDTO);
    }

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponseDTO> getAllProducts(
            @RequestParam(name ="pageNumber",defaultValue = AppConstants.pageNumber, required = false) Integer pageNumber,
            @RequestParam(name ="pageSize", defaultValue = AppConstants.pageSize , required = false) Integer pageSize,
            @RequestParam(name ="sortBy", defaultValue = AppConstants.sortBy, required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = AppConstants.sortDirection, required = false) String sortDir)
     {
            return ResponseEntity.status(HttpStatus.OK).body(productService.geAllProducts(pageNumber,pageSize,sortBy,sortDir));
        }

    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponseDTO> getProductsByCatergory(@PathVariable Long categoryId) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductsByCategory(categoryId));
    }

    @GetMapping("/public/products/{keyword}")
    public ResponseEntity<ProductResponseDTO> getProductsByKeyword(@PathVariable String keyword) {
        return  ResponseEntity.status(HttpStatus.ACCEPTED).body(productService.getProductsByKeyword(keyword));
    }
}
