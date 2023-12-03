package com.ecommerce.product.service.impl;

import com.ecommerce.product.dto.InventoryDto;
import com.ecommerce.product.dto.ProductDto;
import com.ecommerce.product.enums.ResponseCodes;
import com.ecommerce.product.exception.ENFException;
import com.ecommerce.product.model.Product;
import com.ecommerce.product.repository.ProductRepository;
import com.ecommerce.product.service.ProductService;
import com.ecommerce.product.serviceclient.InventoryServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final InventoryServiceClient inventoryServiceClient;
    private final ModelMapper modelMapper;

    @Override
    public List<ProductDto> getAllProductsWithAvailableQuantity() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::mapToProductDtoWithAvailableQuantity)
                .filter(productDto -> productDto.getAvailableQuantity() > 0)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto getProductByIdWithAvailableQuantity(UUID productId) {
        Product product = getProductById(productId);
        return mapToProductDtoWithAvailableQuantity(product);
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product newProduct = modelMapper.map(productDto, Product.class);
        newProduct.setId(UUID.randomUUID());
        Product savedProduct = productRepository.save(newProduct);
        return mapToProductDtoWithAvailableQuantity(savedProduct);
    }

    @Override
    public ProductDto updateProduct(UUID productId, ProductDto productDto) {
        Product existingProduct = getProductById(productId);
        modelMapper.map(productDto, existingProduct);
        existingProduct.setId(productId);
        Product updatedProduct = productRepository.save(existingProduct);

        return mapToProductDtoWithAvailableQuantity(updatedProduct);
    }

    @Override
    public void deleteProduct(UUID productId) {
        Product product = getProductById(productId);
        productRepository.delete(product);
    }

    @Override
    public List<ProductDto> getProductDetails(List<UUID> productIds) {
        List<Product> products = productRepository.findAllById(productIds);
        return products.stream()
                .map(product ->
                        {
                            ProductDto productDto = modelMapper.map(product, ProductDto.class);
                            productDto.setAvailableQuantity(inventoryServiceClient.getAvailableQuantity(productDto.getInventoryId()).getAvailableQuantity());
                            return productDto;
                        }

                )
                .collect(Collectors.toList());
    }


    private Product getProductById(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ENFException(Product.class,"id",productId, ResponseCodes.ENTITY_NOT_FOUND));
    }

    private ProductDto mapToProductDtoWithAvailableQuantity(Product product) {
        InventoryDto inventoryDto = inventoryServiceClient.getAvailableQuantity(product.getInventoryId());
        return mapToProductDto(product, inventoryDto.getAvailableQuantity());
    }

    private ProductDto mapToProductDto(Product product, int availableQuantity) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        productDto.setAvailableQuantity(availableQuantity);
        return productDto;
    }
}
