package com.apple.product.controller;

import com.apple.product.ProductApplication;
import com.apple.product.model.Product;
import com.apple.product.model.Sales;
import com.apple.product.repository.ProductRepository;
import com.apple.product.service.ProductService;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ProductApplication.class)
@WebAppConfiguration
public class ProductControllerTest {

    protected MockMvc mockMvc;
    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    ProductService productService;

    @MockBean
    ProductRepository productRepository;
    Product product = new Product();
    @BeforeEach
    public void setUp() {
        product.setProductId(1);
        product.setProductName("Iphone");
        Sales sales = new Sales();
        sales.setProductSold(10);
        sales.setSalesRegion("Hyd");
        sales.setCountry("India");
        product.setSales(Arrays.asList(sales));
        product.setManufactures(new ArrayList<>());
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//        productService.setProductRepository(productRepository);

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
    public void getProductsListTest() throws Exception {
        String uri = "/product";
        productService.insertProducts(product);
        Mockito.when(productRepository.findAll()).thenReturn(Arrays.asList(product));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Assert.assertNotNull(content);
        Product [] products =  objectMapper.readValue(content, Product[].class);

        Mockito.verify(productRepository, Mockito.times(1)).findAll();
        Assert.assertNotNull(products);
        Product productInfo = products[0];
        Assert.assertEquals(productInfo.getProductName(), product.getProductName());
        Assert.assertEquals(productInfo.getProductId(), product.getProductId());


    }

}