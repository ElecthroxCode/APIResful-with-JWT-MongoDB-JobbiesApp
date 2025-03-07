package com.jobbies.jobbies.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jobbies.jobbies.dto.AuthLoginDTO;
import com.jobbies.jobbies.dto.AuthLoginResponseDTO;
import com.jobbies.jobbies.repository.UserRepository;
import com.jobbies.jobbies.util.UtilSecurityJWT;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	

	private UtilSecurityJWT utilSecurityJWT;
	private PasswordEncoder passwordEncoder;
	private UserRepository userRepository;
	
	@Autowired
	public UserDetailsServiceImpl(UserRepository userRepository, UtilSecurityJWT utilSecurityJWT, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.utilSecurityJWT = utilSecurityJWT;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return this.userRepository.findByEmail(username);
	}
	
	///
	
	public AuthLoginResponseDTO loginUser(AuthLoginDTO authLoginDTO) {
		String username = authLoginDTO.username();
		String password = authLoginDTO.password();
		Authentication authenticaction = this.authenticateUser(authLoginDTO.username(), authLoginDTO.password());
		SecurityContextHolder.getContext().setAuthentication(authenticaction);
		
		String accesoToken = utilSecurityJWT.createToken(authenticaction);
	
		return new AuthLoginResponseDTO(username, true, "User loged succesfuly", accesoToken);
	}
	
	public Authentication authenticateUser(String username, String password) {
		UserDetails userDetails = this.loadUserByUsername(username);
		if(userDetails == null) {
			throw  new BadCredentialsException("Invalid username o password");
		}
		
		if(!this.passwordEncoder.matches(password, userDetails.getPassword())) {
			throw  new BadCredentialsException("Invalid password");
		}
		
		return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());
	}
	

}
