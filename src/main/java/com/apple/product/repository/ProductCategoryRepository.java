package com.apple.product.repository;

import com.apple.product.model.ProductCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Stream;

@Repository
public interface ProductCategoryRepository extends CrudRepository<ProductCategory , Integer> {
    public List<ProductCategory> findByCategoryName(String categoryName);

}
