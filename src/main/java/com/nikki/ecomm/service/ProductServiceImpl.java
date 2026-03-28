package com.nikki.ecomm.service;

import com.nikki.ecomm.exceptions.ResourceNotFound;
import com.nikki.ecomm.models.Category;
import com.nikki.ecomm.models.Product;
import com.nikki.ecomm.payload.ProductDTO;
import com.nikki.ecomm.payload.ProductResponseDTO;
import com.nikki.ecomm.repository.CategoryRepository;
import com.nikki.ecomm.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDTO addProduct(Long categoryId, Product product) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFound("Category", "id", categoryId));
        Product savedProduct = new Product();
        savedProduct.setCategory(category);
        savedProduct.setProductName(product.getProductName());
       savedProduct.setImage(product.getImage());
       savedProduct.setDescription(product.getDescription());
       savedProduct.setQuantity(product.getQuantity());
       savedProduct.setPrice(product.getPrice());
       savedProduct.setDiscount(product.getDiscount());
       savedProduct.setSpecialPrice(product.getSpecialPrice());

       productRepository.save(savedProduct);
       return modelMapper.map(savedProduct, ProductDTO.class);
    }




    @Override
    public ProductResponseDTO getProductsByCategory(Long categoryId ) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFound("Category", "id", categoryId));
        List<Product> allProducts = productRepository.findByCategoryCategoryId(categoryId);
        ProductResponseDTO result  =  new ProductResponseDTO();
        List<ProductDTO> productDTO = new ArrayList<>();
        allProducts.stream().forEach(product -> {
            ProductDTO dto = modelMapper.map(product, ProductDTO.class);
            productDTO.add(dto);
        });
        result.setProductDTOList(productDTO);
        return result;
    }

    @Override
    public ProductResponseDTO geAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sortByandOrder = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByandOrder);
        Page<Product> productPagePage = productRepository.findAll(pageDetails);

        List<ProductDTO>  productDTOList = new ArrayList<>();
        productPagePage.stream().forEach(product -> {
            ProductDTO dto = modelMapper.map(product, ProductDTO.class);
            productDTOList.add(dto);
        });
        ProductResponseDTO result  =  new ProductResponseDTO();
        result.setProductDTOList(productDTOList);
        result.setPageNumber(productPagePage.getNumber());
        result.setPageSize(productPagePage.getSize());
        result.setTotalElements(productPagePage.getTotalElements());
        result.setTotalPages(productPagePage.getTotalPages());
        result.setLastPage(productPagePage.isLast());

        return result;
    }

    @Override
    public ProductResponseDTO getProductsByKeyword(String keyword, Integer pageNumber,
                                                   Integer pageSize, String sortBy, String sortDir) {

        Sort sortByandOrder = sortDir.equalsIgnoreCase("asc") ?Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber,pageSize, sortByandOrder);
        Page<Product> returnedProducts =  productRepository.findByKeyword(keyword, pageDetails);

        List<ProductDTO> productDTO = new ArrayList<>();
        returnedProducts.stream().forEach(product -> {
            ProductDTO dto = modelMapper.map(product, ProductDTO.class);
            productDTO.add(dto);
        });
        ProductResponseDTO result  =  new ProductResponseDTO();
        result.setProductDTOList(productDTO);
        result.setPageNumber(returnedProducts.getNumber());
        result.setPageSize(returnedProducts.getSize());
        result.setTotalElements(returnedProducts.getTotalElements());
        result.setTotalPages(returnedProducts.getTotalPages());
        result.setLastPage(returnedProducts.isLast());
        return  result;
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product foundProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFound("Product", "id", productId));
        productRepository.delete(foundProduct);
        return modelMapper.map(foundProduct, ProductDTO.class);

    }

    @Override
    public ProductDTO updateProduct(Long productId, Product product) {
        Product foundProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFound("Product", "id", productId));
        foundProduct.setProductName(product.getProductName());
        foundProduct.setDescription(product.getDescription());
        foundProduct.setImage(product.getImage());
        foundProduct.setQuantity(product.getQuantity());
        foundProduct.setPrice(product.getPrice());
        foundProduct.setDiscount(product.getDiscount());
        foundProduct.setSpecialPrice(product.getSpecialPrice());
        productRepository.save(foundProduct);
        return modelMapper.map(foundProduct, ProductDTO.class);
    }

}
