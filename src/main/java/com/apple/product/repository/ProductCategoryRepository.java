package com.apple.product.repository;

import com.apple.product.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory , Integer> {
    public List<ProductCategory> findByCategoryName(String categoryName);

}
