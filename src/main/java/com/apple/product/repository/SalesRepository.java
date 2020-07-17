package com.apple.product.repository;

import com.apple.product.model.Sales;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface SalesRepository extends CrudRepository<Sales, String > {
public List<Sales> findBysalesDate(LocalDate salesDate);
}
