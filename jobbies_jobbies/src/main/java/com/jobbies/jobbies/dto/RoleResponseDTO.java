package com.jobbies.jobbies.dto;


import java.util.Set;

import com.jobbies.jobbies.domain.Role;




public record RoleResponseDTO(
		String name,
		Set<String> permissions
		) {
	
	public RoleResponseDTO(Role role) {
		this(role.getName(), role.getPermissions());
	}

}
