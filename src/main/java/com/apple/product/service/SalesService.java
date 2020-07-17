package com.apple.product.service;

import com.apple.product.model.Manufacture;
import com.apple.product.model.Product;
import com.apple.product.model.Sales;
import com.apple.product.repository.ProductRepository;
import com.apple.product.repository.SalesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class SalesService {
    Logger logger = LoggerFactory.getLogger(SalesService.class);
    @Autowired
    SalesRepository salesRepository;
    @Autowired
    ProductService productService;
    public List<Sales> findSalesList() {
        logger.info(" get sales info ");
        List<Sales> sales = (List<Sales>) salesRepository.findAll();
        return sales;
    }
    public Sales findSalesById(String salesId){
        logger.info("get sales info based on sales ID : "+salesId );
        Optional<Sales> salesOptional = salesRepository.findById(salesId);
        if(salesOptional.isPresent()) {
            return salesOptional.get();
        }
        else {
            logger.error("sales data is empty");
            return null;
        }
    }
    public void insertSales(Sales sales) {
        salesRepository.save(sales);
        logger.info("slaes info successfully inserted : "+sales.getSalesDate());
    }
    public void updateSales(Sales sales) {
        salesRepository.save(sales);
        logger.info("sales data successfully updated.");
    }
    public void deleteSales(Sales sales) {
        salesRepository.delete(sales);
        logger.info("dales data successfully deleted.");
    }

    public Set<Product> findProductBySalesDate(LocalDate salesDate) {
        Set<Product> products = new HashSet<>();
        List<Sales> sales = new ArrayList<>();
        List<Integer> productIds = new ArrayList<Integer>();
        logger.trace("salesDate : "+salesDate);
        List<Sales> ProdSalesByDate = getSalesByDate( salesDate );
        logger.trace(" Size : "+ProdSalesByDate.size());
        ProdSalesByDate.forEach(salesInfo -> {
            if(salesInfo!=null && salesInfo.getProduct()!=null) {
                productIds.add(salesInfo.getProduct().getProductId());
            }
        });

        productIds.forEach(productId->{
            Product product = productService.findProductById(productId) ;
            ProdSalesByDate.forEach(salesdata -> {
                if(salesdata.getProduct().getProductId()==productId) {
                    sales.add(salesdata);
                }
            });
            product.getSales().clear();
            product.getSales().addAll(sales);
            sales.clear();
            products.add(product);
        });

        return products;
    }
    public List<Sales> getSalesByDate(LocalDate salesDate) {
        return  salesRepository.findBysalesDate(salesDate);
    }
}
