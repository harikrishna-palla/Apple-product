package com.apple.product.service;

import com.apple.product.model.Manufacture;
import com.apple.product.model.Product;
import com.apple.product.model.Sales;
import com.apple.product.repository.ManufactureRepository;
import com.apple.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
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
public class ManufactureServiceTest {
    @Autowired
    private ManufactureService manufactureService;
    @MockBean
    private ManufactureRepository manufactureRepository;
    Manufacture manufacture = new Manufacture();
    Product product;
    @BeforeEach
    public void init() {
        manufacture.setCount(10);
        manufacture.setCountry("India");
        manufacture.setManufactureDate(LocalDate.parse("2020-07-16"));
        manufacture.setManufactureId(1);
        manufacture.setManufactureRegion("AP");
        manufacture.setProduct(product);

    }
    @Test
    public void findManufactureListTest() throws Exception {
        when(manufactureRepository.findAll()).thenReturn(Stream.of(manufacture).collect(Collectors.toList()));
        List<Manufacture> manufactureList = manufactureService.findManufactureList();
        assertNotNull(manufactureList);
        assertEquals(1, manufactureList.size());

    }
    @Test
    public void findManufactureByIdTest() throws Exception {
        Mockito.when(manufactureRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(manufacture));
        Manufacture manufactureIdInfo = manufactureService.findManufactureById(manufacture.getManufactureId());
        assertNotNull(manufactureIdInfo);
        assertEquals(manufacture.getManufactureId(), manufactureIdInfo.getManufactureId());
    }
    @Test
    public void insertmanufacturesTest() throws Exception{
        Mockito.when(manufactureRepository.save(Mockito.any(Manufacture.class))).thenReturn(manufacture);
        manufactureService.insertmanufactures(manufacture);
        Mockito.verify(manufactureRepository, Mockito.times(1)).save(manufacture);
    }
    @Test
    public void updateManufacturesTest() throws Exception {
        when(manufactureRepository.save(Mockito.any(Manufacture.class))).thenReturn(manufacture);
        manufactureService.updateManufactures(manufacture);
        Mockito.verify(manufactureRepository, Mockito.times(1)).save(manufacture);
    }
    @Test
    public void deleteManufacturesTest() throws Exception{
        doNothing().when(manufactureRepository).delete(Mockito.any(Manufacture.class));

        manufactureService.deleteManufactures(manufacture);
        verify(manufactureRepository, Mockito.times(1)).delete(manufacture);
    }
    /*@Test
    public void deleteManufactureByIdTest() throws Exception{
        doNothing().when(manufactureRepository).deleteById(Mockito.anyInt());
        manufactureService.findManufactureById(manufacture.getManufactureId());
        verify(manufactureRepository, Mockito.times(1)).deleteById(Mockito.anyInt());
    }*/
    @Test
    public void findProductsByCountryTest() throws Exception {
        when(manufactureRepository.findByCountry(Mockito.anyString())).thenReturn(Stream.of(manufacture).collect(Collectors.toList()));
        List<Manufacture> manufactureList = manufactureService.getManufacturerByCountry(manufacture.getCountry());
        assertNotNull(manufactureList);
        assertEquals(manufacture.getCountry(), manufactureList.get(0).getCountry());
    }
}

