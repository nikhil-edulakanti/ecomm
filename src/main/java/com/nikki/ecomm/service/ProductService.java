package com.nikki.ecomm.service;

import com.nikki.ecomm.models.Product;
import com.nikki.ecomm.payload.ProductDTO;
import com.nikki.ecomm.payload.ProductResponseDTO;
import org.jspecify.annotations.Nullable;

public interface ProductService  {
    ProductDTO addProduct(Long categoryId, Product product);

    ProductResponseDTO getProductsByCategory(Long categoryId);


    ProductResponseDTO geAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    ProductResponseDTO getProductsByKeyword(String keyword);
}
