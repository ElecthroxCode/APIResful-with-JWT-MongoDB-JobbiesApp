package com.jobbies.jobbies.domain;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;

import com.jobbies.jobbies.dto.RoleResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collation = "roles")
public class Role {
	private String name;
	private Set<String> permissions = new HashSet<>();
	
	public Role(RoleResponseDTO roleResponseDTO) {
		this.name = roleResponseDTO.name();
		this.permissions = roleResponseDTO.permissions();
	}
	
}
