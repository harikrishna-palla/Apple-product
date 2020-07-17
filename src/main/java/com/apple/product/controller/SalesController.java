package com.apple.product.controller;

import com.apple.product.model.Product;
import com.apple.product.model.ProductCategory;
import com.apple.product.model.Sales;
import com.apple.product.service.ProductService;
import com.apple.product.service.SalesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class SalesController {
    Logger logger = LoggerFactory.getLogger(SalesController.class);
    @Autowired
    SalesService salesService;
    @Autowired
    ProductService productService;
    @GetMapping("/sales")
    public List<Sales> getAllSalesList() {
        logger.info("Sales list info");
        return salesService.findSalesList();
    }
    @GetMapping("/sales/{salesId}")
    public Sales getSalesById(@PathVariable String saleId) {
        return salesService.findSalesById(saleId);
    }
    @GetMapping("/sales/salesDate/{salesDate}")
    public ResponseEntity<Set<Product>> findProductBySalesDate(@RequestParam("salesDate") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate salesDate) {
        logger.debug("salesDate : "+salesDate);
        Set<Product> products = salesService.findProductBySalesDate(salesDate);
        logger.info("product size : "+products.size());
        if(products!=null && products.size()>0){
            return ResponseEntity.status(HttpStatus.OK)
                    .body( products);
        }
        else {
            logger.error("product date is null or empty ");
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new HashSet<Product>());
        }
    }

    @PostMapping("/product/{productID}/sales")
    public void addSales(@RequestBody Sales sales, @PathVariable int productID) {
        Product product = productService.findProductById(productID);
        sales.setProduct(product);
        logger.trace("product data : "+product.getProductName()+ "  "+product.getProductDescription());
        salesService.insertSales(sales);
    }
    @PutMapping("/product/{productID}/sales")
    public void updateSales(@RequestBody Sales sales, @PathVariable int productID) {
        Product product = productService.findProductById(productID);
        sales.setProduct(product);
        salesService.updateSales(sales);
        logger.info("Sales date successfully updated");
    }
    @DeleteMapping("/sales/{saleId}")
    public void deleteSales(@PathVariable Sales sales) {
        logger.info("sales data successfully deleted.");
        salesService.deleteSales(sales);
    }
}
