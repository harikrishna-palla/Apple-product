package com.apple.product.controller;

import com.apple.product.ProductApplication;
import com.apple.product.model.Product;
import com.apple.product.model.ProductCategory;
import com.apple.product.model.Sales;
import com.apple.product.repository.ProductCategoryRepository;
import com.apple.product.service.ProductCategoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ProductApplication.class)
@WebAppConfiguration
public class ProductCategoryControllerTest {
    String json = "";

    protected MockMvc mockMvc;
    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    ProductCategoryService productCategoryService;

    @Mock
    ProductCategoryRepository productCategoryRepository;


    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        productCategoryService.setProductCategoryRepository(productCategoryRepository);

    }

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

    @Test
    public void createProductCategoryTest() throws Exception{
        String uri = "/prodCategory/productCategory";
        ProductCategory productCategory = getProductCategory();
        Mockito.when(productCategoryRepository.save(Mockito.any(ProductCategory.class))).thenReturn(productCategory);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(productCategory))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();


        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(HttpStatus.OK.value(), status);
        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Assert.assertNotNull(content);
        Mockito.verify(productCategoryRepository, Mockito.times(1)).save(Mockito.any(ProductCategory.class));

    }

    private ProductCategory getProductCategory(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("HCL");
        productCategory.setCategoryId(1);
        productCategory.setProducts(Arrays.asList(getProduct()));
        return productCategory;
    }

    private Product getProduct(){
        Product product= new Product();
        product.setProductId(1);
        product.setProductName("Java");
        Sales seller = new Sales();
        seller.setProductSold(10);
        seller.setSalesRegion("Hyd");
        seller.setCountry("India");
        product.setSales(Arrays.asList(seller));
        product.setManufactures(new ArrayList<>());
        return product;
    }

}
