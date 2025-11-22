package com.unaiz.springkart.service;

import com.unaiz.springkart.dto.ProductDTO;
import com.unaiz.springkart.dto.ProductResponse;
import com.unaiz.springkart.entity.Category;
import com.unaiz.springkart.entity.Product;
import com.unaiz.springkart.exception.APIException;
import com.unaiz.springkart.exception.ResourceNotFoundException;
import com.unaiz.springkart.repository.CategoryRepository;
import com.unaiz.springkart.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    FileService fileService;

    @Value("${product.image}")
    private String path;

    @Override
    public ProductDTO addProduct(Long categoryId, ProductDTO productDTO) {
        if (productRepository.findByProductNameAndCategoryCategoryId(
                productDTO.getProductName(),
                categoryId) != null) {

            throw new APIException("Product already exists inside this category");
        }
        Category category = getCategory(categoryId);
        productDTO.setImage("default.png");

        productDTO.setSpecialPrice(calculateSpecialPrice(
                productDTO.getPrice(), productDTO.getDiscount()));

        Product product = modelMapper.map(productDTO, Product.class);
        product.setCategory(category);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductResponse getAllProduct(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> productPage = productRepository.findAll(pageable);
        return createProductResponse(productPage);
    }

    @Override
    public ProductResponse getProductsByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Category category = getCategory(categoryId);

        Sort sort = sortOrder.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> productPage = productRepository.findByCategoryOrderByPriceAsc(category, pageable);
        return createProductResponse(productPage);
    }

    @Override
    public ProductResponse getProductByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        Sort sort = sortOrder.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> productPage = productRepository.findByProductNameLikeIgnoreCase("%" + keyword + "%", pageable);
        return createProductResponse(productPage);

    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO, Long productId) {
        Product productFromDB = getProduct(productId);

        Product product = modelMapper.map(productDTO, Product.class);

        productFromDB.setProductName(product.getProductName());
        productFromDB.setDescription(product.getDescription());
        productFromDB.setQuantity(product.getQuantity());
        productFromDB.setDiscount(product.getDiscount());
        productFromDB.setPrice(product.getPrice());

        productDTO.setSpecialPrice(calculateSpecialPrice(
                productDTO.getPrice(), productDTO.getDiscount()));

        Product savedProduct = productRepository.save(productFromDB);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product product = getProduct(productId);

        productRepository.delete(product);

        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {
        Product productFromDB = getProduct(productId);

        String fileName = fileService.uploadImage(path, image);

        productFromDB.setImage(fileName);
        Product updatedProduct = productRepository.save(productFromDB);
        return modelMapper.map(updatedProduct, ProductDTO.class);
    }

//    HELPER METHOD

    private Category getCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
    }

    private Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
    }

    private double calculateSpecialPrice(double price, double discount) {
        return price - (discount / 100.0) * price;
    }

    private ProductResponse createProductResponse(Page<Product> productPage) {
        if (productPage.isEmpty()) {
            throw new APIException("No Product is present.");
        }
        List<ProductDTO> productDTOS = productPage.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        productResponse.setPageNumber(productPage.getNumber());
        productResponse.setPageSize(productPage.getSize());
        productResponse.setTotalPages(productPage.getTotalPages());
        productResponse.setTotalElements(productPage.getTotalElements());
        productResponse.setLastPage(productPage.isLast());

        return productResponse;
    }

}
