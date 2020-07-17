package com.apple.product.service;


import com.apple.product.model.Manufacture;
import com.apple.product.model.Product;
import com.apple.product.model.Sales;
import com.apple.product.repository.ManufactureRepository;
import com.apple.product.repository.SalesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
public class SalesServiceTest {
    @Autowired
    private SalesService salesService;
    @MockBean
    private SalesRepository salesRepository;
    Sales sales = new Sales();
    Product product;
    @BeforeEach
    public void init() {
        sales.setCountry("India");
        sales.setSalesDate(LocalDate.parse("2020-07-16"));
        sales.setProduct(product);
        sales.setProductSold(20);
        sales.setSalesId(1);
        sales.setSalesRegion("AP");

    }
    @Test
    public void findSalesListTest() throws Exception {
        when(salesRepository.findAll()).thenReturn(Stream.of(sales).collect(Collectors.toList()));
        List<Sales> salesList = salesService.findSalesList();
        assertNotNull(salesList);
        assertEquals(1, salesList.size());
    }
    @Test
    public void findSalesByIdTest() throws Exception {
        Mockito.when(salesRepository.findById(Mockito.anyString())).thenReturn(Optional.of(sales));
        Sales salesInfo = salesService.findSalesById(String.valueOf(sales.getSalesId()));
        assertNotNull(salesInfo);
        assertEquals(sales.getSalesId(), salesInfo.getSalesId());
    }
    @Test
    public void insertSalesTest() throws Exception{
        Mockito.when(salesRepository.save(Mockito.any(Sales.class))).thenReturn(sales);
        salesService.insertSales(sales);
        Mockito.verify(salesRepository, Mockito.times(1)).save(sales);
    }
    @Test
    public void updateSalesTest() throws Exception {
        when(salesRepository.save(Mockito.any(Sales.class))).thenReturn(sales);
        salesService.updateSales(sales);
        Mockito.verify(salesRepository, Mockito.times(1)).save(sales);
    }
    @Test
    public void deleteSalesTest() throws Exception{
        doNothing().when(salesRepository).delete(Mockito.any(Sales.class));

        salesService.deleteSales(sales);
        verify(salesRepository, Mockito.times(1)).delete(sales);
    }

    @Test
    public void findProductBySalesDateTest() throws Exception {
        when(salesRepository.findBysalesDate(LocalDate.parse("2020-07-16"))).thenReturn(Stream.of(sales).collect(Collectors.toList()));
        List<Sales> salesList = salesService.getSalesByDate(sales.getSalesDate());
        assertNotNull(salesList);
        assertEquals(sales.getSalesDate(), salesList.get(0).getSalesDate());
    }
}
