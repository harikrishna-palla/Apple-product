package com.apple.product.controller;

import com.apple.product.model.Manufacture;
import com.apple.product.model.Product;
import com.apple.product.model.ProductCategory;
import com.apple.product.repository.ManufactureRepository;
import com.apple.product.service.ManufactureService;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ManufactureControllerTest {
    Logger logger = LoggerFactory.getLogger(ManufactureControllerTest.class);
    @Autowired
    ManufactureService manufactureService;
    @Autowired
    WebApplicationContext context;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    private ManufactureRepository manufactureRepository;
    Manufacture manufacture = new Manufacture();
    Product product;
    private MockMvc mockMvc;
    @BeforeEach
    public void init(){

        manufacture.setManufactureId(1);
        manufacture.setProduct(product);
        manufacture.setManufactureRegion("HYD");
        manufacture.setCountry("India");
        manufacture.setCount(10);
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    @Test
    public void getAllmanufactureListTest() throws Exception {

        Mockito.when(manufactureRepository.findAll()).thenReturn(Arrays.asList(manufacture));


        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/manufacture")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(HttpStatus.OK.value(), status);
        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Assert.assertNotNull(content);
        Mockito.verify(manufactureRepository, Mockito.times(2)).findAll();
    }
    @Test
    public void getProdByIdTest() throws Exception{
        Mockito.when(manufactureRepository.findById(Mockito.anyInt())).thenReturn(java.util.Optional.of(manufacture));

        manufactureService.insertmanufactures(manufacture);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/manufacture/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(HttpStatus.OK.value(), status);
        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Manufacture manufacture1 =  objectMapper.readValue(content, Manufacture.class);
        Mockito.verify(manufactureRepository, Mockito.times(1)).findById(Mockito.anyInt());

        Assert.assertNotNull(manufacture1);
        Assert.assertEquals(manufacture1.getManufactureRegion(), manufacture.getManufactureRegion());
        Assert.assertEquals(manufacture1.getManufactureId(), manufacture.getManufactureId());

    }
    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
    @Test
    public void updateProductsTest() throws Exception{
        String uri = "/product/1/manufacture";
        Mockito.when(manufactureRepository.save(Mockito.any(Manufacture.class))).thenReturn(manufacture);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(manufacture))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Assert.assertNotNull(content);
        Mockito.verify(manufactureRepository, Mockito.times(1)).save(Mockito.any(Manufacture.class));

    }


    @Test
    public void deleteManufacturesTest() throws Exception{

        manufactureService.insertmanufactures(manufacture);

        RequestBuilder requestBuilder1 = MockMvcRequestBuilders
                .delete("/manufacture")
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(manufacture))
                .contentType(MediaType.APPLICATION_JSON);


        MvcResult mvcResult = mockMvc.perform(requestBuilder1).andReturn();
        int status = mvcResult.getResponse().getStatus();

        Assert.assertEquals(HttpStatus.OK.value(), status);
        Mockito.verify(manufactureRepository, Mockito.times(1)).delete(Mockito.any(Manufacture.class));

    }


}
