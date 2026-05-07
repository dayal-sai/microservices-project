package com.project.productservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.productservice.dto.ApiResponse;
import com.project.productservice.dto.ProductDto;
import com.project.productservice.entity.Product;
import com.project.productservice.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	ProductService service;
	
	@PostMapping("/addProducts")
	public Product addProducts(@Valid @RequestBody ProductDto dto ) {
		return service.addProducts(dto);
		
		
	}
	
	 	@GetMapping("/getProducts")
	    public ApiResponse<Page<Product>> getProducts( @RequestParam int page, @RequestParam int size, @RequestParam String sortBy) {
	 		Page<Product> products = service.getProducts(page,size,sortBy);
	 		
	 	return new ApiResponse<>(
	 				"success",
	 	            "Products fetched successfully",
	 	            products);
	      
	    }
	 	
	 	

	 	@GetMapping("/getProducts/{id}")
	    public Product getProductById(@PathVariable Long id) {
	 		
	 		return service.getProductbyId(id);
	      
	    }
	 	
	 	@PutMapping("/updateProduct/{id}")
		public Product updateProducts(@PathVariable Long id, @Valid @RequestBody ProductDto dto) {
	 	   return service.updateProducts(id, dto);
			
			
		}
		
	 	@DeleteMapping("/removeProduct/{id}")
	 	public String deleteProduct(@PathVariable Long id) {
	 		service.deleteProduct(id);
	 		return "Product deleted successfully";
	 	}
	 	
	 	
	 	
	 	
	 	

}
