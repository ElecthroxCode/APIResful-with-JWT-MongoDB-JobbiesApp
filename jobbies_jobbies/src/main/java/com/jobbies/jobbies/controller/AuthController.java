package com.jobbies.jobbies.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobbies.jobbies.dto.AuthLoginDTO;
import com.jobbies.jobbies.dto.AuthLoginResponseDTO;
import com.jobbies.jobbies.service.impl.UserDetailsServiceImpl;

@RestController
@RequestMapping("/api")
public class AuthController {
	
	private UserDetailsServiceImpl userDetailsServiceImplem;
	
	@Autowired
	public AuthController(UserDetailsServiceImpl userDetailsServiceImplem) {
		this.userDetailsServiceImplem = userDetailsServiceImplem;
	}

	@PostMapping("/auth/login")
	public ResponseEntity<AuthLoginResponseDTO> login(@RequestBody AuthLoginDTO authLoginDTO) {
		return ResponseEntity.ok(userDetailsServiceImplem.loginUser(authLoginDTO));
	}

	
	
	
}
