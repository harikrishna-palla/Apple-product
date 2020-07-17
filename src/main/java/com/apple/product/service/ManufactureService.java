package com.apple.product.service;

import com.apple.product.model.Manufacture;
import com.apple.product.model.Product;
import com.apple.product.repository.ManufactureRepository;
import com.apple.product.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ManufactureService {
    Logger logger = LoggerFactory.getLogger(ManufactureService.class);
    @Autowired
    ManufactureRepository manufactureRepository;
    @Autowired
    ProductService productService;
    public List<Manufacture> findManufactureList() {
        List<Manufacture> manufactures = (List<Manufacture>) manufactureRepository.findAll();
        logger.trace("Manufacture info : "+manufactures.size());
        return manufactures;
    }
    public Manufacture findManufactureById(int manufactureId){
        Optional<Manufacture> manufactureOptional = manufactureRepository.findById(manufactureId);
        if(manufactureOptional.isPresent()) {
            return manufactureOptional.get();
        }
        else {
            logger.error("Manufacture data is not available ");
            return null;
        }
    }
    public void insertmanufactures(Manufacture manufacture) {
        manufactureRepository.save(manufacture);
    }
    public void updateManufactures(Manufacture manufacture) {
        manufactureRepository.save(manufacture);
    }
    public void deleteManufactures(Manufacture manufacture) {
        manufactureRepository.delete(manufacture);
    }
    public void deleteManufactureById(int manufactureId) {
        manufactureRepository.deleteById(manufactureId);
    }
    public Set<Product> findProductsByCountry(String country) {
        Set<Product> products = new HashSet<>();
        List<Manufacture> productManufactures = new ArrayList<>();
        List<Integer> productIds = new ArrayList<Integer>();
        List<Manufacture> prodManufactBycountry = getManufacturerByCountry( country );
        logger.trace("manufacture size : "+prodManufactBycountry.size());
        prodManufactBycountry.forEach(manufacture -> {
            if(manufacture!=null && manufacture.getProduct()!=null) {
                productIds.add(manufacture.getProduct().getProductId());
            }
        });

        productIds.forEach(productId->{
            Product product = productService.findProductById(productId) ;
            prodManufactBycountry.forEach(manufacture -> {
                if(manufacture.getProduct().getProductId()==productId) {
                    productManufactures.add(manufacture);
                }
            });
            product.getManufactures().clear();
            product.getManufactures().addAll(productManufactures);
            productManufactures.clear();
            products.add(product);
        });

        return products;
    }
    public Set<Product> findProductsByRegion(String manufacturerRegion) {
        Set<Product> products = new HashSet<>();
        List<Manufacture> productManufacturers = getManufacturerByRegion( manufacturerRegion );
        List<Manufacture> productManufactures = new ArrayList<>();
        List<Integer> productIds = new ArrayList<Integer>();
        /*for(Manufacture manufacturer:productManufacturers){
            if( manufacturer!=null && manufacturer.getProduct()!=null) {
                int productId = manufacturer.getProduct().getProductId();
                productIds.add(productId);
            }
        }*/
        productManufacturers.forEach(manufacture -> {

            if(manufacture!=null && manufacture.getProduct()!=null) {
                productIds.add(manufacture.getProduct().getProductId());
            }
        });
        productIds.forEach(productId->{
            Product product = productService.findProductById(productId) ;
            productManufacturers.forEach(manufacture -> {
                if(manufacture.getProduct().getProductId()==productId) {
                    productManufactures.add(manufacture);
                }
            });
            product.getManufactures().clear();
            logger.trace("Product manufacture data was successfully cleared : "+product.getManufactures().size());
            product.getManufactures().addAll(productManufactures);
            productManufactures.clear();
            products.add(product);
        });
        /*for(int productId :productIds){
            Product product = productService.findProductById(productId) ;
            for(Manufacture manufacture:productManufacturers) {
                logger.info("Manufacture product ID : "+manufacture.getProduct().getProductId()+"--------product ID: "+productId);
               if ( manufacture.getProduct().getProductId()==(productId)) {
                   productManufactures.add(manufacture);
               }
            }
            logger.info("manufacture size-->"+productManufactures.size()+"----"+product.getManufactures().size());
            product.getManufactures().clear();
            logger.info("product manufacture size-->"+product.getManufactures().size());
            product.getManufactures().addAll(productManufactures);
            logger.info("After set product manufacture -->"+product.getManufactures().size()+"---"+productManufactures.size());
            products.add(product);
        }*/
        return  products;
    }
    public List<Manufacture> getManufacturerByRegion(String manufacturerRegion) {
        logger.info("Manufacture Region value : "+manufacturerRegion);
        return manufactureRepository.findByManufactureRegion(manufacturerRegion);
    }
    public List<Manufacture> getManufacturerByCountry(String country) {
        logger.info("Country is : "+country);
        return manufactureRepository.findByCountry(country);
    }
}
