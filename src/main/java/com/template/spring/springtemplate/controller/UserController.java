package com.template.spring.springtemplate.controller;

import com.template.spring.springtemplate.model.User;
import com.template.spring.springtemplate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Collection<User>> getPeople() {
		return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<User> getUser(@PathVariable long id) {
		User person = userRepository.findOneById(id);

		if (person != null) {
			return new ResponseEntity<>(userRepository.findOneById(id), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new User(), HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addUser(@RequestBody User person) {
		return new ResponseEntity<>(userRepository.save(person), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteUser(@PathVariable long id, Principal principal) {
		User currentUser = userRepository.findOneByUsername(principal.getName());
		
		if (currentUser.getId() == id) {
			userRepository.deleteById(id);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
		}
	}

}
