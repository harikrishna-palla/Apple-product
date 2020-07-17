package com.apple.product.service;

import com.apple.product.model.Product;
import com.apple.product.model.ProductCategory;
import com.apple.product.repository.ProductCategoryRepository;
import com.apple.product.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    Logger logger = LoggerFactory.getLogger(ProductService.class);
    @Autowired
    ProductRepository productRepository;
    public List<Product> findProductList() {
        logger.info("Product List method is calling");
        List<Product> products = (List<Product>) productRepository.findAll();
        return products;
    }
    public Product findProductById(int productId){
        logger.info(" get product based on productId : "+productId);
        Optional<Product> productOptional = productRepository.findById(productId);
        if(productOptional.isPresent()) {
            return productOptional.get();
        }
        else {
            logger.error("product data is empty ");
            return null;
        }
    }
public void insertProducts(Product product) {
        productRepository.save(product);
        logger.info("data inserted successfully.");
    }
    public void updateProducts(Product product) {
        productRepository.save(product);
        logger.info("data successfully updated.");
    }
    public void deleteProducts(Product product) {
        productRepository.delete(product);
        logger.info("product data successfully deleted.");
    }
    public void deleteProductById(int productId) {
        productRepository.deleteById(productId);
        logger.info("product ID successfully deleted : "+productId);
    }
    public List<Product> findByProductName(String productName) {
        logger.trace("product name : "+productName);
        return productRepository.findByProductName(productName);
    }
}
