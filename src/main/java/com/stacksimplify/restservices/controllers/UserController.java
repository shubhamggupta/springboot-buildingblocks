package com.stacksimplify.restservices.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.deser.impl.ExternalTypeHandler.Builder;
import com.stacksimplify.restservices.Exception.UserExistsException;
import com.stacksimplify.restservices.Exception.UserNameNotFoundException;
import com.stacksimplify.restservices.Exception.UserNotFoundException;
import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.services.UserService;
import com.sun.istack.NotNull;

//Controller
@RestController
@Validated
public class UserController {
	// Autowire the User Servise

	@Autowired
	private UserService userService;

	@GetMapping("/users")
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}

	// createUser
	// @ReuqestBody Annotation
	// @PostMapping Annotation
	@PostMapping("/users")
	public ResponseEntity<Void> createUser(@Valid @RequestBody User user, UriComponentsBuilder builder) {
		try {
			userService.createUser(user);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(builder.path("/user/{id}").buildAndExpand(user.getUserid()).toUri());
			return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
			
		}catch(UserExistsException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,ex.getMessage());
		}
	}

	// getUserById
	// @GetMapping
	// @PathVariable
	@GetMapping("/user/{id}")
	public Optional<User> getUserById(@PathVariable("id") @Min(1) Long id) {

		try {
			return userService.getUserById(id);
		} catch (UserNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
		}
	}

	// updateUserById
	// @Pathvariable, @RequestBody
	// @PutMapping
	@PutMapping("/update-user/{id}")
	public User updateUserById(@PathVariable("id") Long id, @RequestBody User user) {
		try {
			return userService.updateUserById(id, user);
		} catch (UserNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
	}

	// deleteUserById
	// @DeleteMapping
	// @PathVariable
	@DeleteMapping("/delete/{id}")
	public void deleteUserById(@PathVariable("id") Long id) {
		userService.deleteUserById(id);
	}

	// getUserName
	// @GetMapping
	// @PathVariable
	@GetMapping("/users/byusername/{username}")
	public User getUserByUserName(@PathVariable("username") String username) throws UserNameNotFoundException {

		User user = userService.getUserByUsername(username);
		if(user == null) {
			throw new UserNameNotFoundException("User name '" +username+"' Not found in repository");
		}
		
		return user;
		
	}
}












