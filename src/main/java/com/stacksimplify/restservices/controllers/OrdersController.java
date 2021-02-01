package com.stacksimplify.restservices.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stacksimplify.restservices.Exception.OrderNotFoundException;
import com.stacksimplify.restservices.Exception.UserNameNotFoundException;
import com.stacksimplify.restservices.Exception.UserNotFoundException;
import com.stacksimplify.restservices.entities.Order;
import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.repositories.OrderRepository;
import com.stacksimplify.restservices.repositories.UserRepository;

@RestController
@RequestMapping(value = "/users")
public class OrdersController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderRepository OrderRepository;
	
	@GetMapping("/{userid}/orders")
	public List<Order> getAllOrders(@PathVariable Long userid)throws UserNotFoundException{
		
		Optional<User> userOptional = userRepository.findById(userid);
		if(!userOptional.isPresent()){
			throw new UserNotFoundException("User not found");
		}
		else { 
			return userOptional.get().getOrder();
		}
		
	}
	
	//Create order
	@PostMapping("/{userid}/order")
	public Order createOrder(@PathVariable Long userid, @RequestBody Order order)throws UserNotFoundException {
		
		Optional<User> userOptional = userRepository.findById(userid);
		if(!userOptional.isPresent()){
			throw new UserNotFoundException("User not found");
		}else {
			User user = userOptional.get();
			order.setUser(user);
			return OrderRepository.save(order);
		}
		
	}
	
	@GetMapping("/{userid}/order/{orderid}")
	public Optional<Order> getOrderByOrderId(@PathVariable Long userid,@PathVariable Long orderid) throws UserNotFoundException, OrderNotFoundException {
		
		Optional<User> userOptional = userRepository.findById(userid);
		if(!userOptional.isPresent()){
			throw new UserNotFoundException("User not found");
		}else {
			Optional<Order> orderOptional = OrderRepository.findById(orderid);
			if(!orderOptional.isPresent()) {
				throw new OrderNotFoundException("Order not found");
			}
			else {
				if(userid.equals(orderOptional.get().getUser().getId())) {
					return orderOptional;
				}else {
					throw new UserNotFoundException("Order not related to your account");
				}
				
			}
		}
		
	}
	
	

}
