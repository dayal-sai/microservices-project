package com.order.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.order.service.client.ProductClient;

@SpringBootTest
class OrderServiceApplicationTests {
	
	@Mock
	private ProductClient productClient;

	@Test
	void contextLoads() {
	}

}
