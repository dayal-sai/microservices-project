package com.order.service.service;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.order.service.client.ProductClient;
import com.order.service.dto.OrderDto;
import com.order.service.entity.Order;
import com.order.service.exception.ResourceNotFoundException;
import com.order.service.exception.ServiceDownException;
import com.order.service.repository.OrderRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class OrderService {
	
	@Autowired
	OrderRepository OrderRespos;
	
	@Autowired
	ProductClient client;
	
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	public List<Order> getOrders(){
		
		return OrderRespos.findAll();
		
	}
	

	public Order addOrders(OrderDto dto) {
		//call product service
		 Object product = client.getProduct(dto.getProductId()); // may throw feign exception

	        Order order = new Order();
	        order.setProductId(dto.getProductId());
	        order.setQuantity(dto.getQuantity());

	      Order saved = OrderRespos.save(order);
	        
	        sendOrderEvent("Order created with id: " + saved.getId());
	        return saved;


	}
	
    // Fallback method
	public Order fallbackMethod(OrderDto dto,Exception ex ) {
		System.out.println("Fallback triggered: " + ex.getClass());
		throw new ServiceDownException("Product service is down. please try again");
		
		
	}
	
	public Order getOrderbyId(Long id) {
		
		return OrderRespos.findById(id).orElseThrow(()-> new ResourceNotFoundException("Order not found"));
		
	}
	
	public Order updateOrders(Long id, OrderDto dto) {
		Order Order = getOrderbyId(id);
		Order.setProductId(dto.getProductId());
	    Order.setQuantity(dto.getQuantity());
		return OrderRespos.save(Order);
	}
	
	public void deleteOrder(Long id) {
		Order Order = getOrderbyId(id);
		OrderRespos.delete(Order);
		
	}
	
	 public void sendOrderEvent(String message) {
	        rabbitTemplate.convertAndSend("order-queue", message);
	 }

}
