package com.apple.product.repository;

import com.apple.product.model.Product;
import com.apple.product.model.ProductCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
    public List<Product> findByProductName(String productName);

}