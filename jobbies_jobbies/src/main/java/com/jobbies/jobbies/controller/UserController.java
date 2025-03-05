package com.jobbies.jobbies.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobbies.jobbies.dto.UserDTO;
import com.jobbies.jobbies.dto.UserResponseDTO;
import com.jobbies.jobbies.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
	
	private UserService userService;
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/users-register")
	public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserDTO userDTO){
		return ResponseEntity.ok(userService.createUser(userDTO));
	}
	
	@GetMapping("/users/{idUser}")
	public ResponseEntity<UserResponseDTO> getUser(@PathVariable String idUser){
		return ResponseEntity.ok(userService.getUserById(idUser));
	}
	
	@PutMapping("/users/{idUser}")
	public ResponseEntity<UserResponseDTO> updateUser(String idUser, UserDTO userDTO){
		return ResponseEntity.ok(userService.updateUser(idUser, userDTO));
	}
	
	@DeleteMapping("/users/{idUser}")
	public ResponseEntity<Void> deleteUser(String idUser){
		userService.userDeleteById(idUser);
		return ResponseEntity.noContent().build();
	}
	
	
}
