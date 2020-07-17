package com.apple.product.controller;

import com.apple.product.model.ProductCategory;
import com.apple.product.service.ProductCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductCategoryController {
    Logger logger = LoggerFactory.getLogger(ProductCategoryController.class);
    @Autowired
    ProductCategoryService productCategoryService;
    @GetMapping("/health")
    public String getHelthCondition() {
        logger.info("health check : Application up and running");
        logger.trace("Trace enabled : Application up and running");
        logger.warn("warning : application up and running with debug mode ");
        logger.debug("application running in debug mode");
        logger.error("application enabled error logs");
        return "Application up and running";
    }
    @GetMapping("/prodCategory")

    public List<ProductCategory> getAllprodCategoryList() {
        logger.info("Product category List method is calling ");
        logger.trace("Category List : "+productCategoryService.findProductCategoryList().size());
        return productCategoryService.findProductCategoryList();
    }
    @GetMapping("/prodCategory/{prodCategoryID}")
    public ProductCategory getProdCategoryById(@PathVariable Integer prodCategoryID) {
        logger.info("Test get data -->"+prodCategoryID);
        logger.info("Product id "+prodCategoryID);
        return productCategoryService.findProdCategoryById(prodCategoryID);
    }
    @GetMapping("/prodCategory/categoryName/{categoryName}")
    public List<ProductCategory> findByCategoryName(@PathVariable String categoryName) {
        return productCategoryService.findByCategoryName(categoryName);
    }
    @PostMapping("/prodCategory/productCategory")
    public void addProdCategories(@RequestBody ProductCategory productCategory) {
        logger.info("Test data -->"+productCategory);
    productCategoryService.insertProductCategories(productCategory);
    logger.trace("productCategory : "+String.valueOf(productCategory.toString()));
    }
    @PutMapping("/prodCategory")
    public void updateProdCategories(@RequestBody ProductCategory productCategory) {
        productCategoryService.updateProductCategories(productCategory);
        logger.trace("productCategory : "+String.valueOf(productCategory.toString()));
    }
    @DeleteMapping("/prodCategory")
    public void deleteProdCategories(@RequestBody ProductCategory productCategory) {
        productCategoryService.deleteProductCategories(productCategory);
        logger.info(productCategory.getCategoryId()+ " : deleted successfully.");
    }
    @DeleteMapping("/prodCategory/{categoryId}")
    public void deleteProdCategoryById(@PathVariable int categoryId) {
        productCategoryService.deleteProductCategoryById(categoryId);
        logger.info(categoryId+ " : deleted successfully.");
    }
}
