package com.apple.product.controller;

import com.apple.product.model.Manufacture;
import com.apple.product.model.Product;
import com.apple.product.model.ProductCategory;
import com.apple.product.service.ManufactureService;
import com.apple.product.service.ProductService;
import org.graalvm.compiler.nodes.memory.address.PluginFactory_OffsetAddressNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class ManufactureController {
    Logger logger = LoggerFactory.getLogger(ManufactureController.class);
    @Autowired
    ManufactureService manufactureService;
    @Autowired
    ProductService productService;
    @GetMapping("/manufacture")
    public List<Manufacture> getAllmanufactureList() {
        logger.info("Manufacture List method is calling ");
        logger.trace(manufactureService.findManufactureList().toString());
        return manufactureService.findManufactureList();
    }
    @GetMapping("/manufacture/{manufactureID}")
    public Manufacture getProdById(@PathVariable int manufactureID) {
        logger.info("Retrieving data based on manufacture ID");
        return manufactureService.findManufactureById(manufactureID);
    }
    @GetMapping("/manufacture/manufactureRegion/{manufactureRegion}")
    public ResponseEntity<Set<Product>> getProductByRegion(@PathVariable String manufactureRegion){
        Set<Product> products =manufactureService.findProductsByRegion(manufactureRegion);
        logger.trace("Product info :" +products.size());
        if(products!=null && products.size()>0){
            return ResponseEntity.status(HttpStatus.OK)
                    .body( products);
        }
        else {
            logger.warn("product objet value is null");
            logger.error("product data is empty");
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new HashSet<Product>());
        }

    }
    @GetMapping("/manufacture/country/{country}")
    public ResponseEntity<Set<Product>> getProductByCountry(@PathVariable String country) {
        Set<Product> products = manufactureService.findProductsByCountry(country);
        logger.info("country value : "+country);
        if(products!=null && products.size()>0){
            return ResponseEntity.status(HttpStatus.OK)
                    .body( products);
        }
        else {
            logger.warn("products value is null");
            logger.error("Products is not available "+products.size());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new HashSet<Product>());
        }
    }

    @PostMapping("/product/{productID}/manufacture")
    public void addManufactures(@RequestBody Manufacture manufacture, @PathVariable int productID) {
        Product product = productService.findProductById(productID);
        manufacture.setProduct(product);
        manufactureService.insertmanufactures(manufacture);
        logger.info("manufacture data was inserted successfully");
    }
    @PutMapping("/product/{productID}/manufacture")
    public void updateManufactures(@RequestBody Manufacture manufacture, @PathVariable int productID) {
        Product product = productService.findProductById(productID);
        manufacture.setProduct(product);
        manufactureService.updateManufactures(manufacture);
        logger.info("manufacture data was updated successfully");
    }
    @DeleteMapping("/manufacture")
    public void deleteManufactures(@RequestBody Manufacture manufacture) {
        manufactureService.deleteManufactures(manufacture);
        logger.info("manufacture data was deleted successfully");
    }
    @DeleteMapping("/manufacture/{manufactureId}")
    public void deleteManufacturesById(@PathVariable int manufactureId) {
        manufactureService.deleteManufactureById(manufactureId);
        logger.info("manufacture data was inserted successfully. deleted Id is : "+manufactureId);
    }
}
