package com.project.productservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import com.project.productservice.dto.ProductDto;
import com.project.productservice.entity.Product;
import com.project.productservice.exception.ResourceNotFoundException;
import com.project.productservice.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	ProductRepository ProductRespos;
	
	@Value("${server.port}")
	private String port; 
	
	private static final Logger log =  LoggerFactory.getLogger(ProductService.class);
	
	@Cacheable("products")
	public Page<Product> getProducts(int page, int size, String sortBy){
		log.info("Fetching from DB...");
		Pageable pageable  = PageRequest.of(page, size, Sort.by(sortBy));
		return ProductRespos.findAll(pageable);
		
	}
	
	public Product addProducts(ProductDto dto) {
		Product product = new Product();
		product.setName(dto.getName());
	    product.setDescription(dto.getDescription());
	    product.setPrice(dto.getPrice());
	    product.setQuantity(dto.getQuantity());
		return ProductRespos.save(product);
	}
	
	@Cacheable(value = "product", key = "#id")
	public Product getProductbyId(Long id) {
		log.info("Fetching product from DB: {}", id);
		return ProductRespos.findById(id).orElseThrow(()-> new ResourceNotFoundException("Product not found"));
		
	}
	
	public Product updateProducts(Long id, ProductDto dto) {
		Product product = getProductbyId(id);
		product.setName(dto.getName());
	    product.setDescription(dto.getDescription());
	    product.setPrice(dto.getPrice());
	    product.setQuantity(dto.getQuantity());
		return ProductRespos.save(product);
	}
	
	public void deleteProduct(Long id) {
		Product product = getProductbyId(id);
		ProductRespos.delete(product);
		
	}
	

}
