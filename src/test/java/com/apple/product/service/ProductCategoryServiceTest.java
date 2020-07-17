package com.apple.product.service;

import com.apple.product.model.Product;
import com.apple.product.model.ProductCategory;
import com.apple.product.repository.ProductCategoryRepository;
import org.junit.Before;
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

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryServiceTest {
    @Autowired
    private ProductCategoryService productCategoryService;
    @MockBean
    private ProductCategoryRepository productCategoryRepository;
    ProductCategory productCategory= new ProductCategory();
    List<Product> products;
    @BeforeEach
    public void init() {
        productCategory.setCategoryId(1);
        productCategory.setCategoryName("Apple");
        productCategory.setCategoryDescription("Apple pvt ltd");
        productCategory.setProducts(products);

    }
    @Test
    public void findProductCategoryListTest() throws Exception {
        when(productCategoryRepository.findAll()).thenReturn(Stream.of(productCategory).collect(Collectors.toList()));
        List<ProductCategory> productCategoryTest = productCategoryService.findProductCategoryList();
        assertNotNull(productCategoryTest);
        assertEquals(1, productCategoryTest.size());

    }
    @Test
    public void findProdCategoryByIdTest() throws Exception {
        Mockito.when(productCategoryRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(productCategory));
        ProductCategory productCategoryIdInfo = productCategoryService.findProdCategoryById(productCategory.getCategoryId());
        assertNotNull(productCategoryIdInfo);
        assertEquals(productCategoryIdInfo.getCategoryId(), productCategory.getCategoryId());
    }
    @Test
    public void insertProductCategoriesTest() throws Exception{
        Mockito.when(productCategoryRepository.save(Mockito.any(ProductCategory.class))).thenReturn(productCategory);
        productCategoryService.insertProductCategories(productCategory);
        Mockito.verify(productCategoryRepository, Mockito.times(1)).save(productCategory);

    }
    @Test
    public void updateProductCategoriesTest() throws Exception {
        when(productCategoryRepository.save(Mockito.any(ProductCategory.class))).thenReturn(productCategory);
        productCategoryService.updateProductCategories(productCategory);
        Mockito.verify(productCategoryRepository, Mockito.times(1)).save(productCategory);
    }
    @Test
    public void deleteProductCategoriesTest() throws Exception{
        doNothing().when(productCategoryRepository).delete(Mockito.any(ProductCategory.class));

        productCategoryService.deleteProductCategories(productCategory);
        verify(productCategoryRepository, Mockito.times(1)).delete(productCategory);
    }
    @Test
    public void deleteProductCategoryById() throws Exception{
        doNothing().when(productCategoryRepository).deleteById(Mockito.anyInt());
        productCategoryService.deleteProductCategoryById(productCategory.getCategoryId());
        verify(productCategoryRepository, Mockito.times(1)).deleteById(Mockito.anyInt());
    }
    @Test
    public void findByCategoryNameTest() throws Exception {
        when(productCategoryRepository.findByCategoryName(Mockito.anyString())).thenReturn(Stream.of(productCategory).collect(Collectors.toList()));
        List<ProductCategory> productCategoryByNameinfo = productCategoryService.findByCategoryName(productCategory.getCategoryName());
        assertNotNull(productCategoryByNameinfo);
        assertEquals(productCategory.getCategoryName(), productCategoryByNameinfo.get(0).getCategoryName());
    }
}
