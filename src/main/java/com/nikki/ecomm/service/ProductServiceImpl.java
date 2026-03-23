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
    public ProductResponseDTO getProductsByKeyword(String keyword) {
        List<Product> returnedProducts = productRepository.findByProductName(keyword);
        List<ProductDTO> productDTO = new ArrayList<>();
        returnedProducts.stream().forEach(product -> {
            ProductDTO dto = modelMapper.map(product, ProductDTO.class);
            productDTO.add(dto);
        });

       // return new ProductResponseDTO(productDTO);
        return null;
    }


}
