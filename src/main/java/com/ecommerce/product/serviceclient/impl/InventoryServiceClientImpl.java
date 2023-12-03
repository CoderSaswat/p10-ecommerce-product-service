package com.ecommerce.product.serviceclient.impl;

import com.ecommerce.product.dto.InventoryDto;
import com.ecommerce.product.serviceclient.InventoryServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryServiceClientImpl implements InventoryServiceClient {
    private final RestTemplate restTemplate;

    @Value("${inventoryServiceBaseUrl}")
    private String inventoryServiceBaseUrl;


    public InventoryDto getAvailableQuantity(UUID inventoryId) {
        String url = String.format("%s/inventory/%s", inventoryServiceBaseUrl, inventoryId);
        ParameterizedTypeReference<InventoryDto> responseType =
                new ParameterizedTypeReference<>() {
                };
        ResponseEntity<InventoryDto> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                responseType
        );
        return  responseEntity.getBody();
    }
}
