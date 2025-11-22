package com.unaiz.springkart.service;

import com.unaiz.springkart.dto.ProductDTO;
import com.unaiz.springkart.dto.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
    ProductResponse getAllProduct(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductDTO addProduct(Long categoryId, ProductDTO productDTO);

    ProductResponse getProductsByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductResponse getProductByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductDTO updateProduct(ProductDTO productDTO, Long productId);

    ProductDTO deleteProduct(Long productId);

    ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException;
}
