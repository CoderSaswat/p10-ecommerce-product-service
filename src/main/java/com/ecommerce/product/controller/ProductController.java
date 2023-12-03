package com.ecommerce.product.controller;

import com.ecommerce.product.dto.ProductDto;
import com.ecommerce.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<ProductDto> getAllProducts() {
        return productService.getAllProductsWithAvailableQuantity();
    }

    @GetMapping("/{productId}")
    public ProductDto getProductById(@PathVariable UUID productId) {
        return productService.getProductByIdWithAvailableQuantity(productId);
    }

    @PostMapping
    public ProductDto createProduct(@RequestBody ProductDto productDto) {
        return productService.createProduct(productDto);
    }

    @PutMapping("/{productId}")
    public ProductDto updateProduct(@PathVariable UUID productId, @RequestBody ProductDto productDto) {
        return productService.updateProduct(productId, productDto);
    }

    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable UUID productId) {
        productService.deleteProduct(productId);
    }

    @PostMapping("/details")
    public List<ProductDto> getProductDetails(@RequestBody List<UUID> productIds) {
        return productService.getProductDetails(productIds);
    }


}
