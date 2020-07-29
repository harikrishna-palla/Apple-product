package com.apple.product.controller;

import com.apple.product.model.Manufacture;
import com.apple.product.model.Product;
import com.apple.product.model.ProductCategory;
import com.apple.product.model.Sales;
import com.apple.product.repository.SalesRepository;
import com.apple.product.service.SalesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SalesControllerTest {
    Logger logger = LoggerFactory.getLogger(ManufactureControllerTest.class);
    @Autowired
    SalesService salesService;
    @Autowired
    WebApplicationContext context;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    SalesRepository salesRepository;
    Sales sales = new Sales();
    Product product;
    private MockMvc mockMvc;
    @BeforeEach
    public void init(){

        sales.setSalesId(1);
        sales.setProduct(product);
        sales.setSalesRegion("HYD");
        sales.setCountry("India");
        sales.setProductSold(10);
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    @Test
    public void getAllSalesListTest() throws Exception {

        Mockito.when(salesRepository.findAll()).thenReturn(Arrays.asList(sales));


        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/sales")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(HttpStatus.OK.value(), status);
        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Assert.assertNotNull(content);
        Mockito.verify(salesRepository, Mockito.times(1)).findAll();
    }
    @Test
    public void addSalesTest() throws Exception{
        String uri = "/product/1/sales";
        Mockito.when(salesRepository.save(Mockito.any(Sales.class))).thenReturn(sales);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(sales))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Assert.assertNotNull(content);
        Mockito.verify(salesRepository, Mockito.times(1)).save(Mockito.any(Sales.class));

    }


    @Test
    public void updateSalesTest() throws Exception{
        String uri = "/product/1/sales";
        Mockito.when(salesRepository.save(Mockito.any(Sales.class))).thenReturn(sales);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(sales))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Assert.assertNotNull(content);
        Mockito.verify(salesRepository, Mockito.times(1)).save(Mockito.any(Sales.class));

    }


    @Test
    public void deleteSalesTest() throws Exception{

        salesService.deleteSales(sales);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/sales")
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(sales))
                .contentType(MediaType.APPLICATION_JSON);


        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        int status = mvcResult.getResponse().getStatus();

        Assert.assertEquals(HttpStatus.OK.value(), status);
        Mockito.verify(salesRepository, Mockito.times(2)).delete(Mockito.any(Sales.class));

    }

    @Test
    public void updateProductCategoryTest() throws Exception{
        String uri = "/product/1/sales";
        Mockito.when(salesRepository.save(Mockito.any(Sales.class))).thenReturn(sales);
        salesService.updateSales(sales);
        Mockito.when(salesRepository.save(Mockito.any(Sales.class))).thenReturn(sales);
        sales.setSalesRegion("AP");
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(uri)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(sales))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();


        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(HttpStatus.OK.value(), status);
        String content = mvcResult.getResponse().getContentAsString();
        Assert.assertNotNull(content);
        Mockito.verify(salesRepository, Mockito.times(2)).save(Mockito.any(Sales.class));

    }

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }



}
