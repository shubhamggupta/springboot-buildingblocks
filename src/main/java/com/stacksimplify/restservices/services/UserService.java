package com.stacksimplify.restservices.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.stacksimplify.restservices.Exception.UserExistsException;
import com.stacksimplify.restservices.Exception.UserNotFoundException;
import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.repositories.UserRepository;

//service
@Service
public class UserService {
	// Autowire The Repository

	@Autowired
	private UserRepository userRepository;

	// getAllUsers method
	public List<User> getAllUsers() {

		return userRepository.findAll();

	}

	// createUser method
	public User createUser(User user) throws UserExistsException {

		User existingUser = userRepository.findByUsername(user.getUsername());
		
		if(existingUser != null) {
			throw new UserExistsException("User already exist");
		}
		
		return userRepository.save(user);

	}

	// getUserById
	public Optional<User> getUserById(Long id) throws UserNotFoundException {

		Optional<User> user = userRepository.findById(id);
		
		if(!user.isPresent()) {
			throw new UserNotFoundException("User not found in user repository");
		}

		return user;
	}

	// updateUserById
	public User updateUserById(Long id, User user) throws UserNotFoundException {

		Optional<User> optionaluser = userRepository.findById(id);
		if(!optionaluser.isPresent()) {
			throw new UserNotFoundException("User not found in user repository, Provide correct user id");
		}

		user.setId(id);
		return userRepository.save(user);

	}

	// deleteUserById
	public void deleteUserById(Long id) {

		Optional<User> optionaluser = userRepository.findById(id);
		if(!optionaluser.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"User not found in user repository, Provide correct user id");
		}
		
		userRepository.deleteById(id);
		
	}

	// getUserName
	public User getUserByUsername(String username) {

		return userRepository.findByUsername(username);
		
	}

}
