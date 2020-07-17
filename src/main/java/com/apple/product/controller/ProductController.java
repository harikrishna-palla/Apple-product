package com.apple.product.controller;

import com.apple.product.model.Product;
import com.apple.product.model.ProductCategory;
import com.apple.product.service.ProductCategoryService;
import com.apple.product.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    Logger logger = LoggerFactory.getLogger(ManufactureController.class);
    @Autowired
    ProductService productService;
    @Autowired
    ProductCategoryService productCategoryService;
    @GetMapping("/product")
    public List<Product> getAllprodList() {
        logger.info("Product List method is calling");
        return productService.findProductList();
    }
    @GetMapping("/product/{productID}")
    public Product getProdById(@PathVariable int productID) {
        logger.trace("Product Id : "+productID);
        return productService.findProductById(productID);
    }

    @GetMapping("/product/productName/{productName}")
    public List<Product> findByProductName(@PathVariable String productName) {
        logger.trace("Product name : "+productName);
        return productService.findByProductName(productName);
    }
    @PostMapping("/prodCategory/{prodCategoryID}/product")
    public void addProducts(@RequestBody Product product, @PathVariable int prodCategoryID) {
        ProductCategory productCategory = productCategoryService.findProdCategoryById(prodCategoryID);
        product.setProductCategory(productCategory);
        logger.trace("product info : "+product.getProductName()+ "  "+product.getProductDescription());
        productService.insertProducts(product);
    }
    @PutMapping("/prodCategory/{prodCategoryID}/product")
    public void updateProducts(@RequestBody Product product, @PathVariable int prodCategoryID) {
        ProductCategory productCategory = productCategoryService.findProdCategoryById(prodCategoryID);
        logger.info("product category name :"+ productCategory.getCategoryName());
        product.setProductCategory(productCategory);
        productService.updateProducts(product);
    }
    @DeleteMapping("/product")
    public void deleteProducts(@RequestBody Product product) {
        productService.deleteProducts(product);
        logger.info("product deleted successfully ");
    }
    @DeleteMapping("/product/{productId}")
    public void deleteProductById(@PathVariable int productId) {
        logger.info(productId+" successfully deleted");
        productService.deleteProductById(productId);
    }
}
