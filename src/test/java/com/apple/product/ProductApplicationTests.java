package com.apple.product;

import com.apple.product.model.Product;
import com.apple.product.repository.ProductRepository;
import com.apple.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductApplicationTests {

	@Autowired
	private ProductService productService;
	@MockBean
	private ProductRepository productRepository;
	/*@Test
	public void findProductListTest() {
		when(productRepository.findAll()).thenReturn(Stream.of(new Product(1,"Apple", "Apple Desc"),
				new Product(2,"Apple", "Apple Desc")).collect(Collectors.toList()));
		assertEquals(2, productService.findProductList().size());
	}*/

	/*@Test
	public void findProductByIdTest() {
		int prodById = 2;
		when(productRepository.findById(prodById)).thenReturn(Optional
				.of(new Product(2, "Apple", "Apple Desc")));
		assertEquals(2, prodById);
	}*/
	@Test
	void contextLoads() {
	}

}
