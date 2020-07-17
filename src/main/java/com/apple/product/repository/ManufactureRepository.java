package com.apple.product.repository;

import com.apple.product.model.Manufacture;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManufactureRepository extends CrudRepository<Manufacture, Integer> {

List<Manufacture> findByManufactureRegion(String manufacturerRegion);
List<Manufacture> findByCountry(String country);
}
