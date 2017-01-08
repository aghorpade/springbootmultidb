package com.mm.boot.multidb.controller.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mm.boot.multidb.model.order.Order;
import com.mm.boot.multidb.repository.order.OrderRepository;

@RestController
@RequestMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

	@Autowired
	public OrderRepository orderRepo;

	// REST APi to create user/save user in database
	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public Order createOrder() {
		Order order=new Order();
		order.setCode(12);
		order.setQuantity(1);
		return orderRepo.save(order);
	}


	// REST API to get all users
	@RequestMapping(method = RequestMethod.GET, headers = "Accept=application/json")
	public List<Order> getAllUsers() {

		return orderRepo.findAll();

	}

	
}
