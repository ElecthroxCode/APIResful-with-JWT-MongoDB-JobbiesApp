package com.jobbies.jobbies.dto;

public record AuthLoginResponseDTO(
		String username,
		Boolean status,
		String msg,
		String jwt
		) {

}
