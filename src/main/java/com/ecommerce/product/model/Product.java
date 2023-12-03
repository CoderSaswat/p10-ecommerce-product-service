package com.ecommerce.product.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Getter
@Setter
@Document("products")
public class Product {
    @Id
    private UUID id;
    private String name;
    private String description;
    private Double price;
    private UUID inventoryId;
}
