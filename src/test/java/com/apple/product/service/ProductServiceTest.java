package com.apple.product.service;


import com.apple.product.model.Manufacture;
import com.apple.product.model.Product;
import com.apple.product.model.ProductCategory;
import com.apple.product.model.Sales;
import com.apple.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {
    @Autowired
    private ProductService productService;
    @MockBean
    private ProductRepository productRepository;
    Product product = new Product();
    List<Manufacture> manufactures;
    List<Sales> sales;
    @BeforeEach
    public void init() {
        product.setProductId(1);
        product.setProductName("Iphone");
        product.setProductDescription("Iphone 7 plus ");
        product.setManufactures(manufactures);
        product.setSales(sales);
    }
    @Test
    public void findProductListTest() throws Exception {
        when(productRepository.findAll()).thenReturn(Stream.of(product).collect(Collectors.toList()));
        List<Product> productList = productService.findProductList();
        assertNotNull(productList);
        assertEquals(1, productList.size());

    }
    @Test
    public void findProductByIdTest() throws Exception {
        Mockito.when(productRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(product));
        Product productIdInfo = productService.findProductById(product.getProductId());
        assertNotNull(productIdInfo);
        assertEquals(product.getProductId(),productIdInfo.getProductId());
    }
    @Test
    public void insertProductsTest() throws Exception{
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);
        productService.insertProducts(product);
        Mockito.verify(productRepository, Mockito.times(1)).save(product);
    }
    @Test
    public void updateProductsTest() throws Exception {
        when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);
        productService.updateProducts(product);
        Mockito.verify(productRepository, Mockito.times(1)).save(product);
    }
    @Test
    public void deleteProductsTest() throws Exception{
        doNothing().when(productRepository).delete(Mockito.any(Product.class));

        productService.deleteProducts(product);
        verify(productRepository, Mockito.times(1)).delete(product);
    }
    @Test
    public void deleteProductByIdTest() throws Exception{
        doNothing().when(productRepository).deleteById(Mockito.anyInt());
        productService.deleteProductById(product.getProductId());
        verify(productRepository, Mockito.times(1)).deleteById(Mockito.anyInt());
    }
    @Test
    public void findByProductNameTest() throws Exception {
        when(productRepository.findByProductName(Mockito.anyString())).thenReturn(Stream.of(product).collect(Collectors.toList()));
        List<Product> productInfoByName = productService.findByProductName(product.getProductName());
        assertNotNull(productInfoByName);
        assertEquals(product.getProductName(), productInfoByName.get(0).getProductName());
    }
}
