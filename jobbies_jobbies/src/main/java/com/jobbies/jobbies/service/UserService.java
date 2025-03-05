package com.jobbies.jobbies.service;

import com.jobbies.jobbies.domain.User;
import com.jobbies.jobbies.dto.UserDTO;
import com.jobbies.jobbies.dto.UserResponseDTO;

public interface UserService {
	
	UserResponseDTO createUser(UserDTO userDTO);
	UserResponseDTO getUserById(String idUser);
	void userDeleteById(String idUser);
	UserResponseDTO updateUser(String idUser, UserDTO userDTO);
	User getUserByIdEntity(String idUser);
}
