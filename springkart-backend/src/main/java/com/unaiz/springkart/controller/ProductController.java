package com.unaiz.springkart.controller;

import com.unaiz.springkart.config.AppConstants;
import com.unaiz.springkart.dto.ProductDTO;
import com.unaiz.springkart.dto.ProductResponse;
import com.unaiz.springkart.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(@Valid @RequestBody ProductDTO productDTO, @PathVariable Long categoryId) {
        ProductDTO savedProduct = productService.addProduct(categoryId, productDTO);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts(@RequestParam(name = "pageNumber", required = false, defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
                                                          @RequestParam(name = "pageSize", required = false, defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
                                                          @RequestParam(name = "sortBy", required = false, defaultValue = AppConstants.SORT_PRODUCTS_BY) String sortBy,
                                                          @RequestParam(name = "sortOrder", required = false, defaultValue = AppConstants.SORT_DIR) String sortOrder) {
        ProductResponse productList = productService.getAllProduct(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> getProductsByCategory(@PathVariable Long categoryId,
                                                                 @RequestParam(name = "pageNumber", required = false, defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
                                                                 @RequestParam(name = "pageSize", required = false, defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
                                                                 @RequestParam(name = "sortBy", required = false, defaultValue = AppConstants.SORT_PRODUCTS_BY) String sortBy,
                                                                 @RequestParam(name = "sortOrder", required = false, defaultValue = AppConstants.SORT_DIR) String sortOrder) {
        ProductResponse productResponse = productService.getProductsByCategory(categoryId, pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductsByKeyword(@PathVariable String keyword,
                                                                @RequestParam(name = "pageNumber", required = false, defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
                                                                @RequestParam(name = "pageSize", required = false, defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
                                                                @RequestParam(name = "sortBy", required = false, defaultValue = AppConstants.SORT_PRODUCTS_BY) String sortBy,
                                                                @RequestParam(name = "sortOrder", required = false, defaultValue = AppConstants.SORT_DIR) String sortOrder) {
        ProductResponse productResponse = productService.getProductByKeyword(keyword, pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(productResponse, HttpStatus.FOUND);
    }

    @PutMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO productDTO, @PathVariable Long productId) {
        ProductDTO updatedProduct = productService.updateProduct(productDTO, productId);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId) {
        ProductDTO productDTO = productService.deleteProduct(productId);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    @PutMapping("/products/{productId}/image")
    public ResponseEntity<ProductDTO> updateProductImage(@PathVariable Long productId,
                                                         @RequestParam("image") MultipartFile image) throws IOException {

        ProductDTO updatedProduct = productService.updateProductImage(productId, image);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }


}
