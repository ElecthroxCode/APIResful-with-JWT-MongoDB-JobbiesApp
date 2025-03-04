package com.jobbies.jobbies.dto;


public record UserDTO(
		String fullname,
		String email,
		String password,
		Boolean isEnabled
		) {

}
