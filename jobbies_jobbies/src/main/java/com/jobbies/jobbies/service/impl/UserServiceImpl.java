package com.jobbies.jobbies.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobbies.jobbies.domain.User;
import com.jobbies.jobbies.dto.UserDTO;
import com.jobbies.jobbies.dto.UserResponseDTO;
import com.jobbies.jobbies.repository.UserRepository;

import com.jobbies.jobbies.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	private UserRepository userRepository;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserResponseDTO createUser(UserDTO userDTO) {
		User user = new User(userDTO);
		userRepository.save(user);
		return new UserResponseDTO(user);
	}

	@Override
	public UserResponseDTO getUserById(String idUser) {
		return new UserResponseDTO(getUserByIdEntity(idUser));
	}

	@Override
	public void userDeleteById(String idUser) {
		User user = getUserByIdEntity(idUser);
		userRepository.delete(user);
	}

	@Override
	public UserResponseDTO updateUser(String idUser, UserDTO userDTO) {
		User user = getUserByIdEntity(idUser);
		user.userUpdate(userDTO);
		return new UserResponseDTO(user);
	}

	@Override
	public User getUserByIdEntity(String idUser) {
	    return userRepository.findById(idUser)
	            .orElseThrow(() -> new NoSuchElementException("User not found."));
	}
}