package com.mm.boot.multidb.controller.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mm.boot.multidb.model.customer.Customer;
import com.mm.boot.multidb.repository.customer.CustomerRepository;

@RestController
@RequestMapping(value = "/customers", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {

	@Autowired
	public CustomerRepository customerRepo;

	// REST APi to create user/save user in database
	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public Customer createUser() {
		Customer customer=new Customer();
		customer.setAge(2);
		customer.setName("amit");
		
		return customerRepo.save(customer);
	}


	// REST API to get all users
	@RequestMapping(method = RequestMethod.GET, headers = "Accept=application/json")
	public List<Customer> getAllUsers() {

		return customerRepo.findAll();

	}

	
}
