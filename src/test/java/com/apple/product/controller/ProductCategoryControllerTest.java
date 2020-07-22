package com.apple.product.controller;

import com.apple.product.controller.ProductCategoryController;
import com.apple.product.model.Product;
import com.apple.product.model.ProductCategory;
import com.apple.product.repository.ProductCategoryRepository;
import com.apple.product.service.ProductCategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.models.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryControllerTest {
    Logger logger = LoggerFactory.getLogger(ProductCategoryControllerTest.class);
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    WebApplicationContext context;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    private ProductCategoryRepository productCategoryRepository;
    ProductCategory productCategory = new ProductCategory();
    ObjectMapper om = new ObjectMapper();
    List<Product> products;

    private MockMvc mockMvc;
    @BeforeEach
    public void init(){

        productCategory.setCategoryId(1);
        productCategory.setCategoryName("Apple");
        productCategory.setCategoryDescription("Apple pvt ltd");
        productCategory.setProducts(products);
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    @Test
    public void findProductListTest() throws Exception {
        when(productCategoryRepository.findAll()).thenReturn(Stream.of(productCategory)
                .collect(Collectors.toList()));
        logger.info("productCategory size : "+productCategoryService.findProductCategoryList().size());
        assertEquals(1, productCategoryService.findProductCategoryList().size());
    }
    @Test
    public void getProdCategoryByIdTest() throws Exception {
        int categoryId = 1;
        when(productCategoryRepository.findById(categoryId)).thenReturn(Optional.of(productCategory));
        logger.info("product category ID : "+productCategoryService.findProdCategoryById(categoryId).getCategoryId());
        assertEquals(categoryId, productCategoryService.findProdCategoryById(categoryId).getCategoryId());
        //  assertThat(productCategory.getCategoryId()).isEqualTo(productCategoryService.findProdCategoryById(productCategory.getCategoryId()));
    }
    @Test
    public void getByCategoryNameTest() throws Exception {
        String productCategoryName = "Apple";
        when(productCategoryRepository.findByCategoryName(productCategoryName))
                .thenReturn(Stream.of(productCategory).collect(Collectors.toList()));
        assertEquals(productCategoryName, productCategoryService.findByCategoryName(productCategoryName).get(0).getCategoryName() );
    }

    @org.junit.Test
    public void addProdCategoriesTest() throws Exception{
        Mockito.when(productCategoryRepository.save(Mockito.any(ProductCategory.class))).thenReturn(productCategory);
        Mockito.when(productCategoryService.findProductCategoryList()).thenReturn(Stream.of(productCategory).collect(Collectors.toList()));


        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("http://localhost:8080//prodCategory/productCategory")
                .accept(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(productCategory))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());

        List<ProductCategory> productCategory1 = productCategoryService.findProductCategoryList();
        Assert.assertNotNull(productCategory1);
    }

    @Test
    public void getAllprodCategoryListTest() throws Exception {

       /*mockMvc.perform(post("/prodCategory/addProdCategories",productCategory)
        .contentType("application/json").param("insert prodcat data","true")
        .content(objectMapper.writeValueAsBytes(productCategory))).andExpect(status().isOk());
        ProductCategory productCategory1 = (ProductCategory) productCategoryRepository.findByCategoryName("Apple");
        assertEquals("Apple", productCategory1.getCategoryName());
     */
        String jsonRequest = om.writeValueAsString(productCategory);
        MvcResult result = mockMvc.perform(get("/prodCategory")
                .content(jsonRequest)
        .content(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        ProductCategory productCategory1 = om.readValue(jsonRequest, ProductCategory.class);
        Assert.assertTrue(productCategory1.getCategoryName().equals("Apple"));

    }

    @Test
    public void getProductCategoryByIdTest() throws Exception{

        Mockito.when(productCategoryRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(productCategory));

        productCategoryService.findProdCategoryById(Mockito.anyInt());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/prodCategory/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(HttpStatus.OK.value(), status);
        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        ProductCategory productCategories =  objectMapper.readValue(content, ProductCategory.class);
        Assert.assertNotNull(productCategories);
        Assert.assertEquals(productCategories.getCategoryId(), productCategory.getCategoryId());

    }

}
