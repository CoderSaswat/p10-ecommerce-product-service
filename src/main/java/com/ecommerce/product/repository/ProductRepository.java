package com.ecommerce.product.repository;

import com.ecommerce.product.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends MongoRepository<Product, UUID> {
}
