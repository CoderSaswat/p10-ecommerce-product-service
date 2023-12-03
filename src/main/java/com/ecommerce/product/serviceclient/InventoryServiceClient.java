package com.ecommerce.product.serviceclient;

import com.ecommerce.product.dto.InventoryDto;
import com.ecommerce.product.dto.ProductDto;

import java.util.List;
import java.util.UUID;

public interface InventoryServiceClient {
    InventoryDto getAvailableQuantity(UUID inventoryId);
}
