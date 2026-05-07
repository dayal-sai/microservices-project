package com.order.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.order.service.dto.OrderDto;
import com.order.service.entity.Order;
import com.order.service.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	OrderService service;
	
	@PostMapping("/addOrders")
	public Order addOrders(@Valid @RequestBody OrderDto dto ) {
		return service.addOrders(dto);
		
		
	}
	
	 	@GetMapping("/getOrders")
	    public List<Order> getOrders() {
	 		
	 		return service.getOrders();
	      
	    }
	 	
	 	

	 	@GetMapping("/getOrders/{id}")
	    public Order getOrderById(@PathVariable Long id) {
	 		
	 		return service.getOrderbyId(id);
	      
	    }
	 	
	 	@PutMapping("/updateOrder/{id}")
		public Order updateOrders(@PathVariable Long id, @Valid @RequestBody OrderDto dto) {
	 	   return service.updateOrders(id, dto);
			
			
		}
		
	 	@DeleteMapping("/removeOrder/{id}")
	 	public String deleteOrder(@PathVariable Long id) {
	 		service.deleteOrder(id);
	 		return "Order deleted successfully";
	 	}
}
