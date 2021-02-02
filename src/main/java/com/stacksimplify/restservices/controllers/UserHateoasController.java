package com.stacksimplify.restservices.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.stacksimplify.restservices.Exception.UserNotFoundException;
import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.repositories.UserRepository;
import com.stacksimplify.restservices.services.UserService;

@RestController
@RequestMapping(value = "/hateoas/users")
@Validated
public class UserHateoasController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	// getUserById
	// @GetMapping
	// @PathVariable
	@GetMapping("/{id}")
	public EntityModel<User> getUserById(@PathVariable("id") @Min(1) Long id) {

		try {
			Optional<User> userOptional = userService.getUserById(id);
			User user = userOptional.get();
			Long userid = user.getUserid();
			Link selfLink = ControllerLinkBuilder.linkTo(this.getClass()).slash(userid).withSelfRel();
			user.add(selfLink);
			EntityModel<User> resource = new EntityModel<User>(user);
			return resource;
			
		} catch (UserNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
		}
	}
	
	@GetMapping
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}
}
