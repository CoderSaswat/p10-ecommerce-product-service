package com.ecommerce.product.service;

import com.ecommerce.product.dto.ProductDto;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<ProductDto> getAllProductsWithAvailableQuantity();
    ProductDto getProductByIdWithAvailableQuantity(UUID productId);
    ProductDto createProduct(ProductDto productDto);
    ProductDto updateProduct(UUID productId, ProductDto productDto);
    void deleteProduct(UUID productId);
    List<ProductDto> getProductDetails(List<UUID> productIds);

}
