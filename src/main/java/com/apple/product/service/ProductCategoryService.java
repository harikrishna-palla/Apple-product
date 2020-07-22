package com.apple.product.service;

import com.apple.product.model.Manufacture;
import com.apple.product.model.Product;
import com.apple.product.model.ProductCategory;
import com.apple.product.model.Sales;
import com.apple.product.repository.ProductCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductCategoryService {
    Logger logger = LoggerFactory.getLogger(ProductCategoryService.class);
    @Autowired
    ProductCategoryRepository productCategoryRepository;

    public void setProductCategoryRepository(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }
    public List<ProductCategory> findProductCategoryList() {
        logger.info("Product category List method is calling... ");
        List<ProductCategory> productCategories = (List<ProductCategory>) productCategoryRepository.findAll();
        return productCategories;
    }
    public ProductCategory findProdCategoryById(int categoryId){
        logger.info("Category Id : "+categoryId);
        Optional<ProductCategory> productCategoryOptional = productCategoryRepository.findById(categoryId);
        if(productCategoryOptional.isPresent()) {
            return productCategoryOptional.get();
        }
        else {
            logger.warn("Product category object is null" );
            logger.error("Product category data is empty ");
            return null;
        }
    }
    public void insertProductCategories(ProductCategory productCategory) {
        logger.trace("Product category info : "+productCategory.getCategoryName());
        List<Product> products = productCategory.getProducts();
        if(products.size()!=0) {
        products.forEach(product -> {
            product.setProductCategory(productCategory);
            List<Manufacture> manufactures = product.getManufactures();
            manufactures.forEach(manufacture -> manufacture.setProduct(product));
            product.setManufactures(manufactures);
            List<Sales> sales = product.getSales();
            sales.forEach(sales1 -> sales1.setProduct(product));
            product.setSales(sales);
        });
        }
        productCategory.setProducts(products);
        productCategoryRepository.saveAndFlush(productCategory);
     //   productCategoryRepository.save(productCategory);
    }
    public void updateProductCategories(ProductCategory productCategory) {
        productCategoryRepository.save(productCategory);
        logger.info("data updated successfully");
    }
    public void deleteProductCategories(ProductCategory productCategory) {
        productCategoryRepository.delete(productCategory);
        logger.info(productCategory+" deleted successfully.");
    }
    public void deleteProductCategoryById(int categoryId) {
        productCategoryRepository.deleteById(categoryId);
        logger.info("categoryId successfully deleted : "+categoryId);
    }

    public List<ProductCategory> findByCategoryName(String categoryName) {
        return productCategoryRepository.findByCategoryName(categoryName);
    }
}
